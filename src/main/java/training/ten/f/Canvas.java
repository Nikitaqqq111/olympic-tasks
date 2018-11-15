package training.ten.f;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Canvas {

    private final char[][] field;
    private final char[][] copyField;

    private Canvas(char[][] field, char[][] copyField) {
        this.field = field;
        this.copyField = copyField;
    }

    static Canvas createCanvas() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/ten_f.input");
        List<String> data = downloader.download(path);
        int columnLength = Integer.parseInt(data.get(0).split(" ")[0]);
        int rowLength = Integer.parseInt(data.get(0).split(" ")[1]);
        char[][] field = new char[rowLength][columnLength];
        char[][] copyField = new char[rowLength + 4][columnLength + 4];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = '.';
            }
        }
        for (int i = 0; i < copyField.length; i++) {
            for (int j = 0; j < copyField[i].length; j++) {
                copyField[i][j] = '.';
            }
        }
        data.remove(0);
        for (int i = 0; i < data.size(); i++) {
            char[] row = data.get(i).toCharArray();
            System.arraycopy(row, 0, field[i], 0, row.length);
        }
        return new Canvas(field, copyField);
    }

    int poorCanvas() {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                Piece piece = bfs(i, j, '*', '.');
                if (piece == null) {
                    continue;
                }
                createBorderOnField(piece);
                int holesCount = 0;
                for (int ii = piece.top + 1; ii <= piece.bottom + 3; ii++) {
                    for (int jj = piece.left + 1; jj <= piece.right + 3; jj++) {
                        boolean isFoundedHole = simpleBfs(ii, jj, '.', '#');
                        if (isFoundedHole) {
                            holesCount++;
                        }
                    }
                }
                printCanvas();
                piece.holeCount = holesCount - 1;
                pieces.add(piece);
                cleanBorderOnField(piece);
            }
        }
        int maxHoleCount = 0;
        int minArea = Integer.MAX_VALUE;
        System.out.println(pieces);
        for (Piece piece : pieces) {
            if (piece.holeCount > maxHoleCount) {
                maxHoleCount = piece.holeCount;
                minArea = piece.area;
            } else if (piece.holeCount == maxHoleCount && piece.area < minArea) {
                minArea = piece.area;
            }
        }
        if (maxHoleCount == 0) {
            return 0;
        } else {
            return minArea;
        }
    }

    private void cleanBorderOnField(Piece piece) {
        for (int i = piece.top; i <= piece.bottom + 4; i++) {
            for (int j = piece.left; j <= piece.right + 4; j++) {
                copyField[i][j] = '.';
            }
        }
    }

    private void createBorderOnField(Piece piece) {
        for (int i = piece.top; i <= piece.bottom + 4; i++) {
            copyField[i][piece.left] = '#';
            copyField[i][piece.right + 4] = '#';
        }
        for (int j = piece.left; j <= piece.right + 4; j++) {
            copyField[piece.top][j] = '#';
            copyField[piece.bottom + 4][j] = '#';
        }
    }

    private boolean simpleBfs(int i, int j, char symbol, char newColor) {
        if (copyField[i][j] != symbol) {
            return false;
        }
        Deque<Pair> deque = new ArrayDeque<>();
        Pair initialPair = new Pair(i, j);
        copyField[i][j] = newColor;
        deque.addLast(initialPair);
        while (!deque.isEmpty()) {
            Pair pair = deque.pollFirst();
            if (copyField[pair.i - 1][pair.j] == symbol) {
                copyField[pair.i - 1][pair.j] = newColor;
                Pair nextPair = new Pair(pair.i - 1, pair.j);
                deque.addLast(nextPair);
            }
            if (copyField[pair.i + 1][pair.j] == symbol) {
                copyField[pair.i + 1][pair.j] = newColor;
                Pair nextPair = new Pair(pair.i + 1, pair.j);
                deque.addLast(nextPair);
            }
            if (copyField[pair.i][pair.j - 1] == symbol) {
                copyField[pair.i][pair.j - 1] = newColor;
                Pair nextPair = new Pair(pair.i, pair.j - 1);
                deque.addLast(nextPair);
            }
            if (copyField[pair.i][pair.j + 1] == symbol) {
                copyField[pair.i][pair.j + 1] = newColor;
                Pair nextPair = new Pair(pair.i, pair.j + 1);
                deque.addLast(nextPair);
            }
        }
        return true;
    }

    private Piece bfs(int i, int j, char symbol, char newColor) {
        if (field[i][j] != symbol) {
            return null;
        }
        int[] border = initBorder();
        int area = 1;
        Deque<Pair> deque = new ArrayDeque<>();
        Pair initialPair = new Pair(i, j);
        updateBorder(initialPair, border);
        field[i][j] = newColor;
        copyField[i + 2][j + 2] = symbol;
        deque.addLast(initialPair);
        while (!deque.isEmpty()) {
            Pair pair = deque.pollFirst();
            if (pair.i - 1 >= 0 && field[pair.i - 1][pair.j] == symbol) {
                field[pair.i - 1][pair.j] = newColor;
                copyField[pair.i - 1 + 2][pair.j + 2] = symbol;
                Pair nextPair = new Pair(pair.i - 1, pair.j);
                deque.addLast(nextPair);
                area++;
                updateBorder(nextPair, border);
            }
            if (pair.i + 1 < field.length && field[pair.i + 1][pair.j] == symbol) {
                field[pair.i + 1][pair.j] = newColor;
                copyField[pair.i + 1 + 2][pair.j + 2] = symbol;
                Pair nextPair = new Pair(pair.i + 1, pair.j);
                deque.addLast(nextPair);
                area++;
                updateBorder(nextPair, border);
            }
            if (pair.j - 1 >= 0 && field[pair.i][pair.j - 1] == symbol) {
                field[pair.i][pair.j - 1] = newColor;
                copyField[pair.i + 2][pair.j - 1 + 2] = symbol;
                Pair nextPair = new Pair(pair.i, pair.j - 1);
                deque.addLast(nextPair);
                area++;
                updateBorder(nextPair, border);
            }
            if (pair.j + 1 < field[0].length && field[pair.i][pair.j + 1] == symbol) {
                field[pair.i][pair.j + 1] = newColor;
                copyField[pair.i + 2][pair.j + 1 + 2] = symbol;
                Pair nextPair = new Pair(pair.i, pair.j + 1);
                deque.addLast(nextPair);
                area++;
                updateBorder(nextPair, border);
            }
        }
        return new Piece(area, border[0], border[1], border[2], border[3]);
    }

    private void updateBorder(Pair nextPair, int[] border) {
        if (nextPair.j < border[0]) {
            border[0] = nextPair.j;
        }
        if (nextPair.j > border[1]) {
            border[1] = nextPair.j;
        }
        if (nextPair.i < border[2]) {
            border[2] = nextPair.i;
        }
        if (nextPair.i > border[3]) {
            border[3] = nextPair.i;
        }
    }

    private int[] initBorder() {
        int[] border = new int[4];
        border[0] = field[0].length; //left
        border[1] = -1; //right
        border[2] = field.length; //top
        border[3] = -1; //bottom
        return border;
    }

    void printCanvas() {
        for (int i = 0; i < field.length; i++) {
            System.out.println(Arrays.toString(field[i]));
        }
        System.out.println();
        for (int i = 0; i < copyField.length; i++) {
            System.out.println(Arrays.toString(copyField[i]));
        }
        System.out.println();
        System.out.println();
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

class Piece {
    final int area, left, right, top, bottom;
    int holeCount = 0;

    Piece(int area, int left, int right, int top, int bottom) {
        this.area = area;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "area=" + area +
                ", left=" + left +
                ", right=" + right +
                ", top=" + top +
                ", bottom=" + bottom +
                ", holeCount=" + holeCount +
                '}';
    }
}
