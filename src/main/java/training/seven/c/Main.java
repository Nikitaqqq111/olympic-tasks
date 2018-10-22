package training.seven.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/seven_c.input");
        int n = Integer.parseInt(downloader.download(path).get(0));
        boolean isWinPosition = false;
        while (n != 1) {
            isWinPosition = !isWinPosition;
            if (isWinPosition) {
                n = roundUp(n, 9);
            } else {
                n = roundUp(n, 2);
            }
        }
        if (isWinPosition) {
            System.out.println("Stan wins");
        } else {
            System.out.println("Ollie wins");
        }
    }

    private static int roundUp(int n, int divider) {
        if (n % divider == 0) {
            return n / divider;
        }
        return n / divider + 1;
    }

}
