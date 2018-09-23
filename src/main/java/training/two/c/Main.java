package training.two.c;

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
        Path path = Paths.get("src/main/resources/two_c.input");
        List<String> rows = downloader.download(path);
        int n = Integer.parseInt(rows.get(0));
        rows.remove(0);
        int[][] table = new int[n][n];
        int i = 0, j = 0;
        for (String row : rows) {
            for (String number : row.split(" ")) {
                table[i][j] = Integer.parseInt(number);
                j++;
            }
            i++;
            j = 0;
        }
        int[][] optimalPaths = PathUtils.findMinPath(table);
        Path pathOut = Paths.get("src/main/resources/two_c.output");
        ISaver saver = new Saver();
        saver.save(pathOut, convertToListString(optimalPaths));
    }

    private static List<String> convertToListString(int[][] table) {
        List<String> stringList = new ArrayList<>();
        for (int[] aTable : table) {
            stringList.add(Arrays.toString(aTable));
        }
        return stringList;
    }

}
