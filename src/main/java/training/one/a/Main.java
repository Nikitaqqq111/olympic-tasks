package training.one.a;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Вывести все простые числа от M до N включительно
 *
 * Ограничения: 2 <= M <= N < 300_000
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/one_a.input");
        String[] strings = downloader.download(path).get(0).split(" ");
        int m = Integer.parseInt(strings[0]);
        int n = Integer.parseInt(strings[1]);
        List<String> simpleNumbers = new ArrayList<>();
        for (int number = m; number <= n; number++) {
            if (NumberUtils.isSimple(number)) {
                simpleNumbers.add(String.valueOf(number));
            }
        }

        Path pathOut = Paths.get("src/main/resources/one_a.output");
        ISaver saver = new Saver();
        if (!simpleNumbers.isEmpty()) {
            saver.save(pathOut, simpleNumbers);
        } else {
            saver.save(pathOut, Collections.singletonList("Absent"));
        }

    }

}
