package training.four.c;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final List<Gangster> gangsters = new ArrayList<>();

    private static class Gangster {
        private final int timeToArrive;
        private final int wealth;
        private final int fats;

        private Gangster(int timeToArrive, int wealth, int fats) {
            this.timeToArrive = timeToArrive;
            this.wealth = wealth;
            this.fats = fats;
        }

        @Override
        public String toString() {
            return "Gangster{" +
                    "timeToArrive=" + timeToArrive +
                    ", wealth=" + wealth +
                    ", fats=" + fats +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/four_c.input");
        List<String> input = downloader.download(path);
        List<Integer> times = Arrays.stream(input.get(1).split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> prices = Arrays.stream(input.get(2).split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> fats = Arrays.stream(input.get(3).split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        calculateGangsters(times, prices, fats);
        gangsters.sort(Comparator.comparingInt(gangster -> gangster.timeToArrive));
        int maxIncome = calculateMaxIncome(0, 0, 0);
        System.out.println("Optimal decision: " + calculateMaxIncomeOptimal());
        Path pathOut = Paths.get("src/main/resources/four_c.output");
        ISaver saver = new Saver();
        saver.save(pathOut, Collections.singletonList(String.valueOf(maxIncome)));
    }

    private static int calculateMaxIncome(int index, int time, int doorPosition) {
        Gangster gangster = gangsters.get(index);
        if (index == gangsters.size() - 1) {
            if (isNotAccessible(gangster, time, doorPosition)) {
                return 0;
            } else {
                return gangster.wealth;
            }
        }
        if (isNotAccessible(gangster, time, doorPosition)) {
            return calculateMaxIncome(index + 1, time, doorPosition);
        } else {
            int incomeWithCurrentGangster = calculateMaxIncome(index + 1, gangster.timeToArrive, gangster.fats) + gangster.wealth;
            int incomeWithoutCurrentGangster = calculateMaxIncome(index + 1, time, doorPosition);
            return incomeWithCurrentGangster > incomeWithoutCurrentGangster
                    ? incomeWithCurrentGangster : incomeWithoutCurrentGangster;
        }

    }

    private static int[] gangstersRichment = new int[100 + 2];

    private static int calculateMaxIncomeOptimal() {
        int[][] maxIncomes = new int[2][100 + 3];
        for (int i = 1; i < 30_101; i++) {
            int line = changeLine();
            fillGangsterRichment(i);
            for (int j = 1; j < maxIncomes[0].length - 1; j++) {
                if (line == 1) {
                    maxIncomes[1][j] = getMax(maxIncomes[0][j - 1], maxIncomes[0][j], maxIncomes[0][j + 1]) + gangstersRichment[j];
                } else {
                    maxIncomes[0][j] = getMax(maxIncomes[1][j - 1], maxIncomes[1][j], maxIncomes[1][j + 1]) + gangstersRichment[j];
                }
            }
        }
        return maxIncomes[stateLine][1];
    }

    private static int changeLine() {
        if (stateLine == 0) {
            stateLine = 1;
        } else {
            stateLine = 0;
        }
        return stateLine;
    }

    private static int stateLine = 0;

    private static void fillGangsterRichment(int time) {
        for (int i = 0; i < gangstersRichment.length; i++) {
            gangstersRichment[i] = 0;
        }
        for (Gangster gangster : gangsters) {
            if (gangster.timeToArrive < gangster.fats) {
                continue;
            }
            if (time == gangster.timeToArrive) {
                gangstersRichment[gangster.fats] = gangstersRichment[gangster.fats] + gangster.wealth;
            }
        }
    }

    private static int getMax(int max1, int max2, int max3) {
        return Math.max(Math.max(max1, max2), max3);
    }

    private static boolean isNotAccessible(Gangster gangster, int time, int doorPosition) {
        int deltaTime = gangster.timeToArrive - time;
        int deltaDoorPosition = Math.abs(gangster.fats - doorPosition);
        if (deltaTime == deltaDoorPosition) {
            return false;
        }
        return deltaTime < deltaDoorPosition;
    }

    private static void calculateGangsters(List<Integer> times, List<Integer> prices, List<Integer> fats) {
        for (int i = 0; i < times.size(); i++) {
            gangsters.add(new Gangster(times.get(i), prices.get(i), fats.get(i)));
        }
    }

}
