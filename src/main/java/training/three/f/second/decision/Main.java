package training.three.f.second.decision;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static int[][] table;
    private static int count = 0;

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/three_f.input");
        int n = Integer.parseInt(downloader.download(path).get(0));
        table = new int[n][n];
        calculateSerpenTable(n);
        List<String> result = new ArrayList<>();
        for (int[] aTable : table) {
            result.add(Arrays.toString(aTable));
        }
        Path pathOut = Paths.get("src/main/resources/three_f.output");
        ISaver saver = new Saver();
        saver.save(pathOut, result);

    }

    private static void calculateSerpenTable(int n) {
        for (int c = 0; c < 2 * n; c++) {
            if (c % 2 == 0) {
                for (int i = Math.min(c, n - 1); i >= 0; i--) {
                    if (c - i >= n) {
                        break;
                    }
                    table[i][c - i] = ++count;
                }
            } else {
                for (int j = Math.min(c, n - 1); j >= 0; j--) {
                    if (c - j >= n) {
                        break;
                    }
                    table[c - j][j] = ++count;
                }
            }
        }
    }

}
