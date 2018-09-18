package training.two.c;

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
        Path path = Paths.get("src/main/resources/two_c.input");
        List<String> rows = downloader.download(path);
        int n = Integer.parseInt(rows.get(0));
        rows.remove(0);
        int[][] table = new int[n + 1][n + 1];
        int i = 1, j = 1;
        for (String row : rows) {
            for (String number : row.split(" ")) {
                table[i][j] = Integer.parseInt(number);
                j++;
            }
            i++;
            j = 1;
        }
        for (int p = 0; p < table.length; p++) {
            for (int q = 0; q < table[p].length; q++) {
                if (p == 0 || q == 0) {
                    table[p][q] = 99999;
                }
            }
        }
        char[][] optimalPaths = PathUtils.findMinPath(table);
        System.out.println(Arrays.deepToString(optimalPaths));
    }

}
