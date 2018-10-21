package training.seven.b;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/seven_b.input");
        char[] message = downloader.download(path).get(0).toCharArray();
        int[] counts = new int[message.length + 1];
        counts[0] = 1;
        counts[1] = 1;
        for (int i = 1; i < message.length; i++) {
            int code = Integer.parseInt(String.valueOf(new char[]{message[i - 1], message[i]}));
            if (code >= 0 && code <= 33 && message[i - 1] != '0') {
                counts[i + 1] = counts[i] + counts[i - 1];
            } else {
                counts[i + 1] = counts[i];
            }
        }
        System.out.println("Count: " + counts[counts.length - 1]);
    }

}
