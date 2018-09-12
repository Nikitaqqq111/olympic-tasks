package training.one.b;

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
        Path path = Paths.get("src/main/resources/one_b.input");
        List<String> strings = downloader.download(path);
        int sum = Integer.parseInt(strings.get(0).split(" ")[1]);
        List<Integer> integerList = Arrays.stream(strings.get(1).split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        String result = NumberUtils.calculateSign(integerList, sum);
        Path pathOut = Paths.get("src/main/resources/one_b.output");
        ISaver saver = new Saver();
        saver.save(pathOut, Collections.singletonList(result));
    }

}
