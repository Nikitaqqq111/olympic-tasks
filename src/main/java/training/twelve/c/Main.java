package training.twelve.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        double[] array = downloadData();
        System.out.println(calculateMinGame(array));
    }

    private static double calculateMinGame(double[] array) {
        double[][] game = new double[array.length - 2][array.length - 2];
        for (int i = 0; i < game.length; i++) {
            game[i][i] = array[i] * array[i + 1] * array[i + 2];
        }
        for (int len = 1; len <= game.length; len++) {
            for (int i = 0; i < game.length - len; i++) {
                double minMulti = Double.MAX_VALUE;
                for (int mid = i; mid <= i + len; mid++) {
                    double multi;
                    if (mid == i) {
                        multi = game[mid + 1][i + len]
                                + array[i] * array[mid + 1] * array[i + len + 2];
                    } else if (mid == i + len) {
                        multi = game[i][mid - 1]
                                + array[i] * array[mid + 1] * array[i + len + 2];
                    } else {
                        multi = game[i][mid - 1] + game[mid + 1][i + len]
                                + array[i] * array[mid + 1] * array[i + len + 2];
                    }
                    if (multi < minMulti) {
                        minMulti = multi;
                    }
                }
                game[i][i + len] = minMulti;
            }
        }
        return game[0][game.length - 1];
    }

    static double[] downloadData() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/twelve_c.input");
        String[] strings = downloader.download(path).get(1).split(" ");
        double[] array = new double[strings.length];
        for (int i = 0; i < strings.length; i++) {
            array[i] = Double.parseDouble(strings[i]);
        }
        return array;
    }

}
