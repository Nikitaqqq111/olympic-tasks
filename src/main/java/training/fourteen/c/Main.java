package training.fourteen.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/fourteen_c.input");
        String string = downloader.download(path).get(0);
        String compactString = compact(string);
        System.out.println(compactString);
    }

    private static String compact(String string) {
        int[][] optLengths = new int[string.length()][string.length()];
        boolean[][] isMulti = new boolean[string.length()][string.length()];
        int[][] indexes = new int[string.length()][string.length()];
        for (int i = 0; i < optLengths.length; i++) {
            optLengths[i][i] = 1;
        }
        for (int len = 1; len < string.length(); len++) {
            for (int i = 0; i < string.length() - len; i++) {
                int[] resultConcat = foundMinLengthForConcat(optLengths, i, len);
                int[] resultMulti = new int[2];
                if (foundMinLengthForMulti(string, optLengths, i, len, resultMulti) && resultConcat[0] > resultMulti[0]) {
                    isMulti[i][i + len] = true;
                    indexes[i][i + len] = resultMulti[1];
                    optLengths[i][i + len] = resultMulti[0];
                } else {
                    indexes[i][i + len] = resultConcat[1];
                    optLengths[i][i + len] = resultConcat[0];
                }
            }
        }
        return foundOptimalCompactSeq(0, string.length() - 1, isMulti, indexes, string);
    }

    private static String foundOptimalCompactSeq(int i, int j, boolean[][] isMulti, int[][] indexes, String string) {
        if (i == j) {
            return string.substring(i, i + 1);
        }
        if (!isMulti[i][j]) {
            return foundOptimalCompactSeq(i, indexes[i][j], isMulti, indexes, string) +
                    foundOptimalCompactSeq(indexes[i][j] + 1, j, isMulti, indexes, string);
        } else {
            return indexes[i][j] + "(" +
                    foundOptimalCompactSeq(i, i + (j - i + 1) / indexes[i][j] - 1, isMulti, indexes, string) + ")";
        }
    }

    private static boolean foundMinLengthForMulti(String string, int[][] optLengths, int i, int len, int[] resultMulti) {
        int period = -1;
        int minLength = Integer.MAX_VALUE;
        for (int x = 2; x <= len + 1; x++) {
            int mod = (len + 1) % x;
            if (mod != 0) {
                continue;
            }
            int div = (len + 1) / x;
            if (isPeriod(div, i, len, string)) {
                int length = optLengths[i][i + div - 1] + 2 + getLength(x);
                if (length < minLength) {
                    minLength = length;
                    period = x;
                }
            }

        }
        if (minLength == Integer.MAX_VALUE) {
            return false;
        } else {
            resultMulti[0] = minLength;
            resultMulti[1] = period;
            return true;
        }
    }

    private static int getLength(int div) {
        int count = 0;
        while (div != 0) {
            count++;
            div = div / 10;
        }
        return count;
    }

    private static boolean isPeriod(int div, int i, int len, String string) {
        int index = i;
        while (index + div < i + len) {
            if (string.charAt(index) != string.charAt(index + div)) {
                return false;
            }
            index++;
        }
        return true;
    }

    private static int[] foundMinLengthForConcat(int[][] optLengths, int i, int len) {
        int min = Integer.MAX_VALUE;
        int indexMin = -1;
        for (int middle = i; middle <= i + len - 1; middle++) {
            if (optLengths[i][middle] + optLengths[middle + 1][i + len] < min) {
                min = optLengths[i][middle] + optLengths[middle + 1][i + len];
                indexMin = middle;
            }
        }
        return new int[]{min, indexMin};
    }

}
