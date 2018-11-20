package training.eleven.a;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/eleven_a.input");
        String[] string = downloader.download(path).get(0).split(" ");
        int a1 = Integer.parseInt(string[0]);
        int n = Integer.parseInt(string[1]);
        System.out.println(calculateMemberProgression(a1, n));
    }

    private static int calculateMemberProgression(int a1, int n) {
        if (n <= 10_000) {
            for (int i = 2; i <= n; i++) {
                a1 = (a1 * a1) % 10_000;
            }
            return a1;
        }
        for (int i = 2; i <= 10_001; i++) {
            a1 = (a1 * a1) % 10_000;
        }
        int barrier = a1;
        int k = 10_001;
        do {
            a1 = (a1 * a1) % 10_000;
            k++;
        } while (barrier != a1);
        int q = (n - 10_001) % (k - 10_001);
        for (int i = 1; i <= q; i++) {
            barrier = (barrier * barrier) % 10_000;
        }
        return barrier;
    }

}
