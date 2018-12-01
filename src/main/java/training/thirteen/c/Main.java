package training.thirteen.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/thirteen_c.input");
        String string = downloader.download(path).get(0);
        String initialString = calculateInitialMinString(string);
        System.out.println(initialString);
    }

    private static String addBracket(char bracket) {
        if (bracket == '(' || bracket == ')') {
            return "()";
        }
        if (bracket == '[' || bracket == ']') {
            return "[]";
        }
        throw new IllegalArgumentException();
    }

    private static boolean isCloseBracket(char bracket) {
        return bracket == ')' || bracket == ']';
    }


    private static boolean isOpenBracket(char bracket) {
        return bracket == '(' || bracket == '[';
    }

    private static boolean isOneType(char leftBracket, char rightBracket) {
        if (leftBracket == '(' && rightBracket == ')') {
            return true;
        }
        return leftBracket == '[' && rightBracket == ']';
    }

    private static String calculateInitialMinString(String string) {
        int[][] table = new int[string.length()][string.length()];
        String[][] optStrings = new String[string.length()][string.length()];
        for (int i = 0; i < table.length; i++) {
            table[i][i] = 2;
            optStrings[i][i] = addBracket(string.charAt(i));
        }
        for (int length = 1; length < string.length(); length++) {
            for (int i = 0; i < string.length() - length; i++) {
                if (isCloseBracket(string.charAt(i))) {
                    table[i][i + length] = 2 + table[i + 1][i + length];
                    optStrings[i][i + length] = addBracket(string.charAt(i)) + optStrings[i + 1][i + length];
                    continue;
                }
                if (isOpenBracket(string.charAt(i + length))) {
                    table[i][i + length] = 2 + table[i][i + length - 1];
                    optStrings[i][i + length] = optStrings[i][i + length - 1] + addBracket(string.charAt(i + length));
                    continue;
                }
                int minLength = Integer.MAX_VALUE;
                int optMiddle = -1;
                for (int middle = i; middle <= i + length - 1; middle++) {
                    if (table[i][middle] + table[middle + 1][i + length] < minLength) {
                        minLength = table[i][middle] + table[middle + 1][i + length];
                        optMiddle = middle;
                    }
                }
                if (isOneType(string.charAt(i), string.charAt(i + length)) && table[i + 1][i + length - 1] < minLength) {
                    table[i][i + length] = 2 + table[i + 1][i + length - 1];
                    optStrings[i][i + length] = string.charAt(i) + optStrings[i + 1][i + length - 1] + string.charAt(i + length);
                } else {
                    table[i][i + length] = table[i][optMiddle] + table[optMiddle + 1][i + length];
                    optStrings[i][i + length] = optStrings[i][optMiddle] + optStrings[optMiddle + 1][i + length];
                }
            }
        }
        return optStrings[0][optStrings.length - 1];
    }
}
