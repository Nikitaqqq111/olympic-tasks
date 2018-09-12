package training.one.f;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/one_f.input");
        String input = downloader.download(path).get(0);
        List<Integer> cards = Arrays.stream(input.split(" "))
                .map(Integer::parseInt).collect(Collectors.toList());
        String combination = Poker.calculate(cards);
        System.out.println(combination);

    }

}
