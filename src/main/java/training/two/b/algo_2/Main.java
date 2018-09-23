package training.two.b.algo_2;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/two_b.input");
        char[] symbols = downloader.download(path).get(0).toCharArray();
        List<String> sequences = SequenceUtils.generateAllSequences(symbols);
        Path pathOut = Paths.get("src/main/resources/two_b.output");
        ISaver saver = new Saver();
        saver.save(pathOut, sequences);
    }

}
