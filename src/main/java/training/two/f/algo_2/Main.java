package training.two.f.algo_2;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/two_f.input");
        List<String> input = downloader.download(path);
        int n = Integer.parseInt(input.get(0));
        int[][] spiralTable = new Spiral(n).construct();
        for (int i = 1; i < spiralTable.length - 1; i++) {
            for (int j = 1; j < spiralTable.length - 1; j++) {
                System.out.print(spiralTable[i][j] + " ");
            }
            System.out.println();
        }
    }

}
