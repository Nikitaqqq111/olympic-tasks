package training.three.e;

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
        Path path = Paths.get("src/main/resources/three_e.input");
        List<String> numbers = downloader.download(path);
        int[] firstNumber = parseIntoArray(numbers.get(0));
        int[] secondNumber = parseIntoArray(numbers.get(1));
        int[] multiply = multiply(firstNumber, secondNumber);
        String answer = convertToString(multiply);
        Path pathOut = Paths.get("src/main/resources/three_e.output");
        ISaver saver = new Saver();
        saver.save(pathOut, Collections.singletonList(answer));
    }

    private static int[] parseIntoArray(String number) {
        int[] numberArray = new int[number.length()];
        int j = number.length() - 1;
        for (Character character : number.toCharArray()) {
            numberArray[j] = Integer.parseInt(character.toString());
            j--;
        }
        return numberArray;
    }

    private static int[] multiply(int[] firstNumber, int[] secondNumber) {
        int[] result = new int[firstNumber.length + secondNumber.length];
        for (int i = 0; i < firstNumber.length; i++) {
            int carry = 0;
            for (int j = 0; j < secondNumber.length; j++) {
                carry = firstNumber[i] * secondNumber[j] + result[i + j] + carry;
                result[i + j] = carry % 10;
                carry = carry / 10;
            }
            result[i + secondNumber.length] = carry;
        }
        return result;
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
