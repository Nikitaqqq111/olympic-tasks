package training.nine.f;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static class Rectangle {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        private Rectangle(int x1, int y1, int x2, int y2) {
            if (x1 < x2) {
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;
            } else {
                this.x1 = x2;
                this.y1 = y2;
                this.x2 = x1;
                this.y2 = y1;
            }
            if (y1 > y2) {
                int tmp = this.y1;
                this.y1 = this.y2;
                this.y2 = tmp;
            }
        }

    }

    private static List<Rectangle> rectangles = new ArrayList<>();
    private static List<Integer> xLines = new ArrayList<>();
    private static List<Integer> yLines = new ArrayList<>();

    private static Cell[][] cells;

    private static class Cell {
        boolean goUp = true;
        boolean goRight = true;
        boolean goDown = true;
        boolean goLeft = true;

        final int i;
        final int j;

        boolean paint;

        private Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) throws IOException {
        fillRectangles();
        fillCells();
        System.out.println(paint());
    }

    private static int paint() {
        int count = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].paint) {
                    continue;
                }
                count++;
                bfs(i, j);
            }
        }
        return count;
    }

    private static void bfs(int i, int j) {
        Deque<Cell> deque = new ArrayDeque<>();
        deque.addLast(cells[i][j]);
        cells[i][j].paint = true;
        while (!deque.isEmpty()) {
            Cell currentCell = deque.pollFirst();
            if (currentCell.goRight && currentCell.j + 1 < cells.length && !cells[currentCell.i][currentCell.j + 1].paint) {
                cells[currentCell.i][currentCell.j + 1].paint = true;
                deque.addLast(cells[currentCell.i][currentCell.j + 1]);
            }
            if (currentCell.goLeft && currentCell.j - 1 >= 0 && !cells[currentCell.i][currentCell.j - 1].paint) {
                cells[currentCell.i][currentCell.j - 1].paint = true;
                deque.addLast(cells[currentCell.i][currentCell.j - 1]);
            }
            if (currentCell.goUp && currentCell.i + 1 < cells.length && !cells[currentCell.i + 1][currentCell.j].paint) {
                cells[currentCell.i + 1][currentCell.j].paint = true;
                deque.addLast(cells[currentCell.i + 1][currentCell.j]);
            }
            if (currentCell.goDown && currentCell.i - 1 >= 0 && !cells[currentCell.i - 1][currentCell.j].paint) {
                cells[currentCell.i - 1][currentCell.j].paint = true;
                deque.addLast(cells[currentCell.i - 1][currentCell.j]);
            }
        }

    }

    private static void fillCells() {
        cells = new Cell[yLines.size() - 1][xLines.size() - 1];
        for (int i = 0; i < xLines.size() - 1; i++) {
            for (int j = 0; j < yLines.size() - 1; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        for (Rectangle rectangle : rectangles) {
            colorRectangle(rectangle);
        }
    }

    private static void colorRectangle(Rectangle rectangle) {
        int x1Index = xLines.indexOf(rectangle.x1);
        int x2Index = xLines.indexOf(rectangle.x2);
        int y1Index = yLines.indexOf(rectangle.y1);
        int y2Index = yLines.indexOf(rectangle.y2);
        for (int i = x1Index; i < x2Index; i++) {
            cells[y1Index][i].goDown = false;
            if (y1Index >= 1) {
                cells[y1Index - 1][i].goUp = false;
            }

            cells[y2Index][i].goDown = false;
            cells[y2Index - 1][i].goUp = false;
        }
        for (int j = y1Index; j < y2Index; j++) {
            cells[j][x1Index].goLeft = false;
            if (x1Index >= 1) {
                cells[j][x1Index - 1].goRight = false;
            }

            cells[j][x2Index].goLeft = false;
            cells[j][x2Index - 1].goRight = false;
        }
    }

    private static void fillRectangles() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/nine_f.input");
        List<String> data = downloader.download(path);
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i).split(" ");
            rectangles.add(new Rectangle(Integer.parseInt(row[0]),
                    Integer.parseInt(row[1]),
                    Integer.parseInt(row[2]),
                    Integer.parseInt(row[3])));
            if (xLines.indexOf(Integer.parseInt(row[0])) == -1) {
                xLines.add(Integer.parseInt(row[0]));
            }
            if (yLines.indexOf(Integer.parseInt(row[1])) == -1) {
                yLines.add(Integer.parseInt(row[1]));
            }
            if (xLines.indexOf(Integer.parseInt(row[2])) == -1) {
                xLines.add(Integer.parseInt(row[2]));
            }
            if (yLines.indexOf(Integer.parseInt(row[3])) == -1) {
                yLines.add(Integer.parseInt(row[3]));
            }
        }
        xLines.add(10_001);
        yLines.add(10_001);
        Collections.sort(xLines);
        Collections.sort(yLines);
        xLines.add(0, xLines.get(0) - 1);
        yLines.add(0, yLines.get(0) - 1);
    }

}
