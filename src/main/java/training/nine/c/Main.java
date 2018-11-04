package training.nine.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    private static int[] piles;
    private static int k;
    private static int[] cumPiles;

    public static void main(String[] args) throws IOException {
        downloadData();
        calculateCumPiles();
        System.out.println(calculateMaxPills());
    }

    private static int calculateMaxPills() {
        int[][] table = new int[k + 1][piles.length];
        for (int i = 1; i < table.length; i++) {
            for (int j = piles.length - 1; j >= 0; j--) {
                int minContenderPills = Integer.MAX_VALUE;
                for (int k = 1; k <= i; k++) {
                    minContenderPills = Math.min(minContenderPills, getElement(table, k, j + k));
                }
                table[i][j] = cumPiles[j] - minContenderPills;
            }
        }
        for (int i = 0; i < table.length; i++) {
            System.out.println(Arrays.toString(table[i]));
        }
        return table[k][0];
    }

    private static int getElement(int[][] table, int i, int j) {
        if (j >= table[0].length) {
            return 0;
        }
        return table[i][j];
    }

    private static void calculateCumPiles() {
        cumPiles = new int[piles.length];
        System.arraycopy(piles, 0, cumPiles, 0, cumPiles.length);
        for (int i = cumPiles.length - 2; i >= 0; i--) {
            cumPiles[i] += cumPiles[i + 1];
        }
    }

    private static void downloadData() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/nine_c.input");
        String[] numbers = downloader.download(path).get(0).split(" ");
        k = Integer.parseInt(numbers[numbers.length - 1]);
        piles = new int[numbers.length - 2];
        for (int i = 1; i < numbers.length - 1; i++) {
            piles[i - 1] = Integer.parseInt(numbers[i]);
        }
    }


}
