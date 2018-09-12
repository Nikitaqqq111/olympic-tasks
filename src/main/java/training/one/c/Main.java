package training.one.c;

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
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/one_c.input");
        List<String> strings = downloader.download(path);
        List<Integer> numbers = Arrays.stream(strings.get(1).split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> maxIncSeq = IncreasingSubseq.findMaxSubseq(numbers);
        Path pathOut = Paths.get("src/main/resources/one_c.output");
        ISaver saver = new Saver();
        List<String> answers = new ArrayList<>();
        answers.add(String.valueOf(maxIncSeq.size()));
        answers.add(maxIncSeq.toString());
        saver.save(pathOut, answers);
    }

}
