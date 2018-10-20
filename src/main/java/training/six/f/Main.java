package training.six.f;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static char[][] field;

    public static void main(String[] args) throws IOException {
        fillField();
        paint(1, 1);
        paint(field.length - 2, field.length - 2);
        System.out.println("Square: " + findWall());
    }

    private static int findWall() {
        int count = 0;
        for (int i = 1; i < field.length - 1; i++) {
            for (int j = 1; j < field.length - 1; j++) {
                if (field[i][j] != '$') {
                    continue;
                }
                if (field[i - 1][j] == '#') count++;
                if (field[i + 1][j] == '#') count++;
                if (field[i][j - 1] == '#') count++;
                if (field[i][j + 1] == '#') count++;
            }
        }
        return (count - 4) * 9;
    }

    private static void paint(int i, int j) {
        if (field[i][j] != '.') {
            return;
        }
        field[i][j] = '$';
        paint(i + 1, j);
        paint(i - 1, j);
        paint(i, j + 1);
        paint(i, j - 1);
    }

    private static void fillField() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/six_f.input");
        List<String> data = downloader.download(path);
        int n = Integer.parseInt(data.get(0));
        field = new char[n + 2][n + 2];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = '#';
            }
        }
        for (int i = 1; i < data.size(); i++) {
             char[] line = data.get(i).toCharArray();
             for (int j = 0; j < line.length; j++) {
                 field[i][j + 1] = line[j];
             }
        }
    }

}
