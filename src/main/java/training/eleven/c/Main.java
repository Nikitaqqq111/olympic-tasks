package training.eleven.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/eleven_c.input");
        String string = downloader.download(path).get(0);
        System.out.println(calculatePalindroms(string));
    }

    private static int calculatePalindroms(String string) {
        int[][] table = new int[string.length()][string.length()];
        for (int i = 0; i < string.length(); i++) {
            table[i][i] = 1;
        }
        for (int len = 1; len < string.length(); len++) {
            for (int i = 0; i < table.length; i++) {
                if (i + len >= table.length) {
                    continue;
                }
                if (string.charAt(i) == string.charAt(i + len)) {
                    table[i][i + len] = table[i + 1][i + len] + table[i][i + len - 1] + 1;
                    continue;
                }
                table[i][i + len] = table[i + 1][i + len] + table[i][i + len - 1] - table[i + 1][i + len - 1];
            }
        }
        for (int i = 0; i < string.length(); i++) {
            System.out.println(Arrays.toString(table[i]));
        }
        return table[0][table[0].length - 1];
    }

}
