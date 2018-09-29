package training.four.b;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<List<Integer>> answer = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/four_b.input");
        int number = Integer.parseInt(downloader.download(path).get(0));
        decompose(new ArrayList<>(), number - 1, number);
        List<String> lines = new ArrayList<>();
        for (List<Integer> line : answer) {
            lines.add(line.toString());
        }
        Path pathOut = Paths.get("src/main/resources/four_b.output");
        ISaver saver = new Saver();
        saver.save(pathOut, lines);

    }

    private static void decompose(List<Integer> terms, int prevTerm, int sum) {
        if (sum == 0) {
            answer.add(new ArrayList<>(terms));
            return;
        } else if (sum < 0) {
            return;
        }
        for (int term = 1; term <= prevTerm; term++) {
            terms.add(term);
            decompose(terms, term, sum - term);
            terms.remove(terms.size() - 1);
        }
    }


}
