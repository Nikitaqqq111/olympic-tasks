package training.five.e;

import training.Downloader;
import training.IDownloader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    private static int ii;
    private static int jj;
    private static List<Integer> number = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        fillData();
        divide(number, ii, jj);
    }

    private static void divide(List<Integer> number, int ii, int jj) {
        List<Integer> result = new ArrayList<>();
        while (inNonZero(number)) {
            int index = 0;
            int value = 0;
            List<Integer> wholePart = new ArrayList<>();
            while (index < number.size()) {
                value = value * ii + number.get(index++);
                wholePart.add(value / jj);
                value = value % jj;
            }
            result.add(value);
            number = wholePart;
        }
        System.out.println("Result: ");
        for (int i = result.size() - 1; i >= 0; i--) {
            System.out.print(Character.forDigit(result.get(i), jj));
        }
    }

    private static boolean inNonZero(List<Integer> number) {
        return !number.stream().allMatch(digit -> digit == 0);
    }

    private static boolean isGreaterOrEqual(List<Integer> number, int ii, int jj) {
        boolean isFindNoneZero = false;
        Iterator<Integer> iterator = number.iterator();
        int value = 0;
        while (iterator.hasNext()) {
            Integer digit = iterator.next();
            if (!isFindNoneZero && digit == 0) {
                iterator.remove();
            } else {
                isFindNoneZero = true;
                value = value * ii + digit;
            }
        }
        return value >= jj;
    }


    private static void fillData() throws Exception {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/five_e.input");
        List<String> input = downloader.download(path);
        ii = Integer.parseInt(input.get(0).split(" ")[0]);
        jj = Integer.parseInt(input.get(0).split(" ")[1]);
        char[] numberAsCharArray = input.get(1).toCharArray();
        for (int i = 0; i < numberAsCharArray.length; i++) {
            number.add(Character.digit(numberAsCharArray[i], ii));
        }
    }
}
