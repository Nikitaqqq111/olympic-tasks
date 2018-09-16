package training.two.b;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/two_b.input");
        List<String> strings = downloader.download(path);
        char[] sequence = strings.get(0).toCharArray();
//        List<List<Character>> shuffles = SequenceArray.getAllReShuffle(sequence);
//        System.out.println(shuffles.size());
//        for (List<Character> row : shuffles) {
//            System.out.println(row);
//        }
        SequenceArray.shuffle(sequence, 0, new char[sequence.length]);
        System.out.println(SequenceArray.count);
    }

}
