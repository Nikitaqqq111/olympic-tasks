package training.nine.e;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static int[][] matrix;
    private static int[][] matrixBfs;
    private static int[][] result;


    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        downloadData();
        bfsMatrix();
        Arrays.stream(result).forEach(line -> System.out.println(Arrays.toString(line)));
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private static void bfsMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    continue;
                }
                bfs(i, j);
            }
        }
    }

    private static void bfs(int i, int j) {
        int color = i * matrix.length + j;
        int p = 1;
        int childCount = 0;
        Deque<Cell> deque = new ArrayDeque<>();
        deque.addLast(new Cell(i, j));
        matrixBfs[i][j] = color;
        int candidate = 0;
        while (!deque.isEmpty()) {
            Cell currentCell = deque.pollFirst();
            p--;
            List<Cell> nextCells = getAvailableNextCells(color, currentCell);
            for (Cell cell : nextCells) {
                deque.addLast(cell);
            }
            childCount += nextCells.size();

            if (matrix[currentCell.i][currentCell.j] != 0 && candidate == 0) {
                candidate = matrix[currentCell.i][currentCell.j];
            } else if (matrix[currentCell.i][currentCell.j] != 0 && candidate != 0) {
                candidate = 0;
                break;
            }

            if (p == 0) {
                if (candidate != 0) {
                    break;
                }
                p = childCount;
                childCount = 0;
            }

        }
        result[i][j] = candidate;
    }

    private static List<Cell> getAvailableNextCells(int color, Cell currentCell) {
        List<Cell> nextCells = new ArrayList<>();
        if (currentCell.i - 1 > -1 && matrixBfs[currentCell.i - 1][currentCell.j] != color) {
            nextCells.add(new Cell(currentCell.i - 1, currentCell.j));
            matrixBfs[currentCell.i - 1][currentCell.j] = color;
        }
        if (currentCell.j - 1 > -1 && matrixBfs[currentCell.i][currentCell.j - 1] != color) {
            nextCells.add(new Cell(currentCell.i, currentCell.j - 1));
            matrixBfs[currentCell.i][currentCell.j - 1] = color;
        }
        if (currentCell.i + 1 < matrixBfs.length && matrixBfs[currentCell.i + 1][currentCell.j] != color) {
            nextCells.add(new Cell(currentCell.i + 1, currentCell.j));
            matrixBfs[currentCell.i + 1][currentCell.j] = color;
        }
        if (currentCell.j + 1 < matrixBfs.length && matrixBfs[currentCell.i][currentCell.j + 1] != color) {
            nextCells.add(new Cell(currentCell.i, currentCell.j + 1));
            matrixBfs[currentCell.i][currentCell.j + 1] = color;
        }
        return nextCells;
    }

    private static class Cell {
        final int i;
        final int j;

        private Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private static void downloadData() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/nine_e.input");
        List<String> data = downloader.download(path);
        int n = Integer.parseInt(data.get(0));
        matrix = new int[n][n];
        matrixBfs = new int[n][n];
        result = new int[n][n];
        for (int i = 1; i < data.size(); i++) {
            String[] line = data.get(i).split(" ");
            for (int j = 0; j < line.length; j++) {
                matrix[i - 1][j] = Integer.parseInt(line[j]);
                result[i - 1][j] = Integer.parseInt(line[j]);
                matrixBfs[i - 1][j] = -1;
            }
        }

    }

}
