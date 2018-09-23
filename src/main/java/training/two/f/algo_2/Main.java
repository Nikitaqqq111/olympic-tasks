package training.two.f.algo_2;

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

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/two_f.input");
        List<String> input = downloader.download(path);
        int n = Integer.parseInt(input.get(0));
        int[][] spiralTable = new Spiral(n).construct();
        List<String> table = new ArrayList<>();
        for (int i = 1; i < spiralTable.length - 1; i++) {
            table.add(Arrays.toString(Arrays.copyOfRange(spiralTable[i], 1, spiralTable.length - 1)));
        }
        Path pathOut = Paths.get("src/main/resources/two_f.output");
        ISaver saver = new Saver();
        saver.save(pathOut, table);

    }

}
