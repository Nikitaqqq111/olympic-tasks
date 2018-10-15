package training.six.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static final int GOOD = 1;
    private static final int BAD = 2;
    private static final int UNAVAILABLE = 3;

    private static class Calendar {
        int[][] calendar = new int[31][12];

        final Map<Integer, Integer> availables = new HashMap<>();


        Calendar() {
            calendar[30][11] = GOOD;
        }

        {
            availables.put(0, 30);
            availables.put(1, 28);
            availables.put(2, 30);
            availables.put(3, 29);
            availables.put(4, 30);
            availables.put(5, 29);
            availables.put(6, 30);
            availables.put(7, 30);
            availables.put(8, 29);
            availables.put(9, 30);
            availables.put(10, 29);
            availables.put(11, 30);
        }

        int get(int day, int month) {
            if (day > 30 || month > 11) {
                return UNAVAILABLE;
            }
            int range = availables.get(month);
            if (day > range) {
                return UNAVAILABLE;
            }
            return calendar[day][month];
        }
    }

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/six_c.input");
        int day = Integer.parseInt(downloader.download(path).get(0).split(" ")[0]);
        int month = Integer.parseInt(downloader.download(path).get(0).split(" ")[1]);
        System.out.println("Who win in dates: " + whoWinInDates(day, month));
    }

    private static int whoWinInDates(int dayStart, int monthStart) {
        Calendar calendar = new Calendar();
        for (int month = 11; month >= 0; month--) {
            for (int day = 30; day >= 0; day --) {
                if (month == 11 && day == 30) {
                    continue;
                }
                int firstStep = calendar.get(day + 1, month);
                int secondStep = calendar.get(day + 2, month);
                int thirdStep = calendar.get(day, month + 1);
                int fourthStep = calendar.get(day, month + 2);
                calendar.calendar[day][month] = checkGoodOrBadStep(firstStep, secondStep, thirdStep, fourthStep);
            }
        }
        if (calendar.calendar[dayStart - 1][monthStart - 1] == GOOD) {
            return 1;
        } else {
            return 2;
        }
    }

    private static int checkGoodOrBadStep(int firstStep, int secondStep, int thirdStep, int fourthStep) {
        List<Integer> steps = Arrays.asList(firstStep, secondStep, thirdStep, fourthStep);
        List<Integer> availableSteps = new ArrayList<>();
        for (Integer step : steps) {
            if (step != UNAVAILABLE) {
                availableSteps.add(step);
            }
        }
        if (availableSteps.isEmpty()) {
            return UNAVAILABLE;
        }
        if (availableSteps.stream().allMatch(step -> step == GOOD)) {
            return BAD;
        } else {
            return GOOD;
        }
    }

}
