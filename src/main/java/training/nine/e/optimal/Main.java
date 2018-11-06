package training.nine.e.optimal;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static int[][] matrix;

    private static Map<Integer, Pair[][]> optimalMatrixMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        downloadData();
        calculateDest();
        printMatrix();
    }

    private static void printMatrix() {
        for (int[] aMatrix : matrix) {
            System.out.println(Arrays.toString(aMatrix));
        }
    }

    private static void calculateDest() {
        boolean isChanged = true;
        int count = 0;
        while (isChanged) {
            isChanged = false;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (optimalMatrixMap.get(count % 2)[i][j] != Pair.ZERO) {
                        optimalMatrixMap.get(count % 2 == 0 ? 1 : 0)[i][j] = optimalMatrixMap.get(count % 2)[i][j];
                        continue;
                    }
                    Pair newPair = calculateDestForCell(i, j, count);
                    if (newPair != Pair.ZERO) {
                        isChanged = true;
                    }
                    optimalMatrixMap.get(count % 2 == 0 ? 1 : 0)[i][j] = newPair;
                }
            }
            count++;
        }
        System.out.println(count);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (optimalMatrixMap.get(count % 2)[i][j] == Pair.ZERO
                        || optimalMatrixMap.get(count % 2)[i][j] == Pair.INF) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = matrix[optimalMatrixMap.get(count % 2)[i][j].i][optimalMatrixMap.get(count % 2)[i][j].j];
                }
            }
        }
    }

    private static Pair calculateDestForCell(int i, int j, int count) {
        List<Pair> nextCells = new ArrayList<>();
        if (i - 1 > -1) {
            nextCells.add(optimalMatrixMap.get(count % 2)[i - 1][j]);
        }
        if (j - 1 > -1) {
            nextCells.add(optimalMatrixMap.get(count % 2)[i][j - 1]);
        }
        if (i + 1 < matrix.length) {
            nextCells.add(optimalMatrixMap.get(count % 2)[i + 1][j]);
        }
        if (j + 1 < matrix.length) {
            nextCells.add(optimalMatrixMap.get(count % 2)[i][j + 1]);
        }
        if (nextCells.stream().anyMatch(pair -> pair == Pair.INF)) {
            return Pair.INF;
        }
        if (nextCells.stream().allMatch(pair -> pair == Pair.ZERO)) {
            return Pair.ZERO;
        }
        nextCells.removeIf(pair -> pair == Pair.ZERO);
        for (int k = 0; k < nextCells.size() - 1; k++) {
            if (nextCells.get(k).i == nextCells.get(k + 1).i && nextCells.get(k).j == nextCells.get(k + 1).j) {
                continue;
            }
            return Pair.INF;
        }
        return nextCells.get(0);
    }

    private static void downloadData() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/nine_e.input");
        List<String> data = downloader.download(path);
        int n = Integer.parseInt(data.get(0));
        matrix = new int[n][n];
        optimalMatrixMap.put(0, new Pair[n][n]);
        optimalMatrixMap.put(1, new Pair[n][n]);
        for (int i = 1; i < data.size(); i++) {
            String[] line = data.get(i).split(" ");
            for (int j = 0; j < line.length; j++) {
                matrix[i - 1][j] = Integer.parseInt(line[j]);
                if (matrix[i - 1][j] != 0) {
                    optimalMatrixMap.get(0)[i - 1][j] = new Pair(i - 1, j);
                    continue;
                }
                optimalMatrixMap.get(0)[i - 1][j] = Pair.ZERO;
            }
        }

    }

}

class Pair {
    final int i;
    final int j;

    static final Pair ZERO = new Pair(-1, -1);
    static final Pair INF = new Pair(Integer.MAX_VALUE, Integer.MAX_VALUE);


    Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
