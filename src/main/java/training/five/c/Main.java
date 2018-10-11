package training.five.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/five_c.input");
        List<String> input = downloader.download(path);
        int lengthOfPath = Integer.parseInt(input.get(0).split(" ")[1]);
        int[][] table = fillTable(input);
        int maxPath = getMaxPath(lengthOfPath, table);
        System.out.println("maxPath: " + maxPath);
    }

    private static int getMaxPath(int lengthOfPath, int[][] table) {
        int[][][] optimalPath = new int[2][table.length + 2][table.length + 2];
        for (int path = 0; path < lengthOfPath; path++) {
            int index = path % 2;
            int prevIndex = index == 0 ? 1 : 0;
            for (int i = 1; i < table.length - 1; i++) {
                for (int j = 1; j < table.length - 1; j++) {
                    if (i + j > path + 2) {
                        continue;
                    }
                    optimalPath[index][i][j] = Collections.max(Arrays.asList(
                            optimalPath[prevIndex][i - 1][j],
                            optimalPath[prevIndex][i + 1][j],
                            optimalPath[prevIndex][i][j - 1],
                            optimalPath[prevIndex][i][j + 1])) + table[i - 1][j - 1];
                }
            }
            if (path == lengthOfPath - 1) {
                return findMaxElement(optimalPath[index]);
            }
        }
        throw new RuntimeException("WTF!");
    }

    private static int findMaxElement(int[][] table) {
        int max = table[1][1];
        for (int i = 1; i < table.length - 1; i++) {
            for (int j = 1; j < table.length - 1; j++) {
                if (table[i][j] > max) {
                    max = table[i][j];
                }
            }
        }
        return max;
    }


    private static int[][] fillTable(List<String> input) {
        int n = Integer.parseInt(input.get(0).split(" ")[0]);
        int[][] table = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] numbers = input.get(i + 1).split(" ");
            for (int j = 0; j < n; j++) {
                table[i][j] = Integer.parseInt(numbers[j]);
            }
        }
        return table;
    }


}
