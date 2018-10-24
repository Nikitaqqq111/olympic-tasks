package training.seven.f;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static char[][] field;

    public static void main(String[] args) throws IOException {
        fillField();
        int count = 0;
        for (int i = 1; i < field.length - 1; i++) {
            for (int j = 1; j < field[i].length - 1; j++) {
                if (bfs(new Point(i, j))) {
                    count++;
                }
            }
        }
        System.out.println("Count: " + count);
    }

    private static void fillField() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/seven_f.input");
        List<String> data = downloader.download(path);
        int n = Integer.parseInt(data.get(0).split(" ")[0]);
        int m = Integer.parseInt(data.get(0).split(" ")[1]);
        field = new char[n + 2][m + 2];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = '.';
            }
        }
        for (int i = 1; i < data.size(); i++) {
            char[] line = data.get(i).toCharArray();
            System.arraycopy(line, 0, field[i], 1, line.length);
        }
    }

    private static boolean bfs(Point point) {
        if (field[point.x][point.y] != '#') {
            return false;
        }
        Deque<Point> queue = new LinkedList<>();
        queue.addLast(point);
        while (!queue.isEmpty()) {
            Point currentPoint = queue.pollLast();
            field[currentPoint.x][currentPoint.y] = 'X';
            if (field[currentPoint.x - 1][currentPoint.y] == '#') {
                queue.addLast(new Point(currentPoint.x - 1, currentPoint.y));
            }
            if (field[currentPoint.x + 1][currentPoint.y] == '#') {
                queue.addLast(new Point(currentPoint.x + 1, currentPoint.y));
            }
            if (field[currentPoint.x][currentPoint.y - 1] == '#') {
                queue.addLast(new Point(currentPoint.x, currentPoint.y - 1));
            }
            if (field[currentPoint.x][currentPoint.y + 1] == '#') {
                queue.addLast(new Point(currentPoint.x, currentPoint.y + 1));
            }
        }
        return true;
    }

    private static class Point {
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private final int x, y;

    }

}
