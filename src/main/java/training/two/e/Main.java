package training.two.e;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
        System.out.println(Arrays.toString(sum));
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

}
