package training.four.e;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static String wholePartAsString;
    private static String devidePartAsString;

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/four_e.input");
        List<String> input = downloader.download(path);
        String mAsString = input.get(0);
        List<Integer> m = new ArrayList<>();
        for (Character character : mAsString.toCharArray()) {
            m.add(Integer.parseInt(character.toString()));
        }
        int n = Integer.parseInt(input.get(1));
        divide(m, n);
        Path pathOut = Paths.get("src/main/resources/four_e.output");
        ISaver saver = new Saver();
        saver.save(pathOut, Arrays.asList(wholePartAsString, devidePartAsString));
    }

    private static void divide(List<Integer> m, int n) {
        int k = 0;
        List<Integer> wholePart = new ArrayList<>();
        for (int digital : m) {
            k = k * 10 + digital;
            if (k < n) {
                wholePart.add(0);
                continue;
            }
            wholePart.add(k / n);
            k = k % n;
        }
        boolean isNonZero = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wholePart.size(); i++) {
            if (!isNonZero && wholePart.get(i) == 0) {
                continue;
            }
            isNonZero = true;
            sb.append(wholePart.get(i));

        }
        wholePartAsString = sb.length() != 0 ? sb.toString() : "0";
        devidePartAsString = String.valueOf(k);
    }

}
