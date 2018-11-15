package training.ten.f;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Canvas {

    private final char[][] field;

    private Canvas(char[][] field) {
        this.field = field;
    }

    static Canvas createCanvas() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/ten_f.input");
        List<String> data = downloader.download(path);
        int columnLength = Integer.parseInt(data.get(0).split(" ")[0]);
        int rowLength = Integer.parseInt(data.get(0).split(" ")[1]);
        char[][] field = new char[rowLength + 2][columnLength + 2];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = '.';
            }
        }
        data.remove(0);
        for (int i = 0; i < data.size(); i++) {
            char[] row = data.get(i).toCharArray();
            System.arraycopy(row, 0, field[i + 1], 1, row.length);
        }
        return new Canvas(field);
    }

    int poorCanvas() {
        bfs(0, 0, '.', '#');
        int count = 0;
        Map<Integer, Pair> pieces = new HashMap<>();
        for (int i = 1; i < field.length - 1; i++) {
            for (int j = 1; j < field[i].length - 1; j++) {
                int area = bfs(i, j, '*', Character.forDigit(count, 10));
                if (area != -1) {
                    pieces.put(count++, new Pair(area, 0));
                }
            }
        }
        colorHoles(pieces);
        printCanvas();
        System.out.println(pieces);
        int maxCountHole = 0;
        int minArea = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Pair> entry : pieces.entrySet()) {
            if (entry.getValue().j > maxCountHole) {
                maxCountHole = entry.getValue().j;
                minArea = entry.getValue().i;
            } else if (entry.getValue().j == maxCountHole && entry.getValue().i < minArea) {
                minArea = entry.getValue().i;
            }
        }
        if (maxCountHole == 0) {
            return 0;
        }
        return minArea;
    }

    private void colorHoles(Map<Integer, Pair> pieces) {
        for (int i = 1; i < field.length - 1; i++) {
            for (int j = 1; j < field[i].length - 1; j++) {
                if (field[i][j] == '.') {
                    Set<Character> nearPoints = new HashSet<>();
                    nearPoints.add(field[i - 1][j]);
                    nearPoints.add(field[i + 1][j]);
                    nearPoints.add(field[i][j - 1]);
                    nearPoints.add(field[i][j + 1]);
                    if (nearPoints.size() > 2) {
                        continue;
                    }
                    if (nearPoints.size() == 2 && !nearPoints.contains('.')) {
                        continue;
                    }
                    if (nearPoints.size() == 1 && nearPoints.contains('.')) {
                        continue;
                    }
                    nearPoints.remove('.');
                    Pair pair = pieces.get(Character.digit((Character) nearPoints.toArray()[0], 10));
                    pair.j = pair.j + 1;
                    bfs(i, j, '.', '#');
                }
            }
        }
    }

    private void printCanvas() {
        for (int i = 0; i < field.length; i++) {
            System.out.println(Arrays.toString(field[i]));
        }
    }

    private int bfs(int i, int j, char symbol, char newColor) {
        if (field[i][j] != symbol) {
            return -1;
        }
        int area = 1;
        Deque<Pair> deque = new ArrayDeque<>();
        Pair initialPair = new Pair(i, j);
        field[i][j] = newColor;
        deque.addLast(initialPair);
        while (!deque.isEmpty()) {
            Pair pair = deque.pollFirst();
            if (pair.i - 1 >= 0 && field[pair.i - 1][pair.j] == symbol) {
                field[pair.i - 1][pair.j] = newColor;
                deque.addLast(new Pair(pair.i - 1, pair.j));
                area++;
            }
            if (pair.i + 1 < field.length && field[pair.i + 1][pair.j] == symbol) {
                field[pair.i + 1][pair.j] = newColor;
                deque.addLast(new Pair(pair.i + 1, pair.j));
                area++;
            }
            if (pair.j - 1 >= 0 && field[pair.i][pair.j - 1] == symbol) {
                field[pair.i][pair.j - 1] = newColor;
                deque.addLast(new Pair(pair.i, pair.j - 1));
                area++;
            }
            if (pair.j + 1 < field[0].length && field[pair.i][pair.j + 1] == symbol) {
                field[pair.i][pair.j + 1] = newColor;
                deque.addLast(new Pair(pair.i, pair.j + 1));
                area++;
            }
        }
        return area;
    }
}

class Pair {
    int i, j;

    Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
