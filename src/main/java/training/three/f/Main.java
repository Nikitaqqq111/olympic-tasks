package training.three.f;

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

public class Main {

    private static int count = 0;
    private static int initialX = 0;
    private static int initialY = 0;
    private static int direction = 1;
    private static int[][] serpenTable;


    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/three_f.input");
        int n = Integer.parseInt(downloader.download(path).get(0));
        createSerpenTable(n);
        List<String> table = new ArrayList<>();
        for (int[] aSerpenTable : serpenTable) {
            table.add(Arrays.toString(aSerpenTable));
        }
        Path pathOut = Paths.get("src/main/resources/three_f.output");
        ISaver saver = new Saver();
        saver.save(pathOut, table);
    }

    private static void createSerpenTable(int n) {
        serpenTable = new int[n][n];
        int lineNumber = 0;
        for (int i = 0; i < 2 * n - 1; i++) {
            fillLine();
            lineNumber++;
            calculateInitialPoint(lineNumber);
        }
    }

    private static void calculateInitialPoint(int lineNumber) {
        if (direction == 1) {
            initialY = Math.min(initialX + 1, serpenTable.length - 1);
            initialX = lineNumber - initialY;
            direction = -1;
        } else if (direction == -1) {
            initialX = Math.min(initialY + 1, serpenTable.length - 1);
            initialY = lineNumber - initialX;
            direction = 1;
        }
    }

    private static void fillLine() {
        int currentX = initialX;
        int currentY = initialY;
        int n = serpenTable.length;
        while (currentX >= 0 && currentX < n && currentY >= 0 && currentY < n) {
            serpenTable[currentX][currentY] = ++count;
            if (direction == 1) {
                currentX--;
                currentY++;
            } else if (direction == -1) {
                currentX++;
                currentY--;
            } else {
                throw new IllegalArgumentException("Invalid direction!");
            }
        }

    }

}
