package training.two.b.algo_2;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/two_b.input");
        char[] symbols = downloader.download(path).get(0).toCharArray();
        SequenceUtils.generateAllSequences(symbols);
        System.out.println(SequenceUtils.count);
    }

}
