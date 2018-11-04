package training.nine.b;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<List<Integer>> answer = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/nine_b.input");
        int n = Integer.parseInt(downloader.download(path).get(0).split(" ")[0]);
        int q = Integer.parseInt(downloader.download(path).get(0).split(" ")[1]);
        System.out.println(getProbability(n, q));

    }

    private static double getProbability(int n, int q) {
        double[][] table = new double[n + 1][q + 1];
        table[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= q; j++) {
                for (int k = 1; k <= 6; k++) {
                    if (j - k < 0) {
                        break;
                    }
                    table[i][j] += table[i - 1][j - k];
                }
                table[i][j] /= 6;
            }
        }
        return table[n][q];
    }

}
