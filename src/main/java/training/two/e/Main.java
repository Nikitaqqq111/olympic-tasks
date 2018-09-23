package training.two.e;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/two_e.input");
        List<String> numbers = downloader.download(path);
        int[] firstNumber = convertNumberFromString(numbers.get(0));
        int[] secondNumber = convertNumberFromString(numbers.get(1));
        int[] sum;
        if (firstNumber.length > secondNumber.length) {
            sum = Summator.sum(firstNumber, secondNumber);
        } else {
            sum = Summator.sum(secondNumber, firstNumber);
        }
        Path pathOut = Paths.get("src/main/resources/two_e.output");
        ISaver saver = new Saver();
        saver.save(pathOut, Collections.singletonList(convertToString(sum)));
    }

    private static int[] convertNumberFromString(String number) {
        int[] digits = new int[number.length()];
        int index = 0;
        char[] charArray = number.toCharArray();
        for (int i = charArray.length - 1; i >= 0; i--) {
            digits[index] = Integer.parseInt(Character.valueOf(charArray[i]).toString());
            index++;
        }
        return digits;
    }

    private static String convertToString(int[] number) {
        List<Integer> integers = new ArrayList<>();
        boolean foundNonZero = false;
        for (int i = number.length - 1; i >= 0; i--) {
            if (!foundNonZero && number[i] == 0) {
                continue;
            }
            foundNonZero = true;
            integers.add(number[i]);
        }
        StringBuilder sb = new StringBuilder();
        for (Integer integer : integers) {
            sb.append(integer);
        }
        return sb.toString();
    }

}
