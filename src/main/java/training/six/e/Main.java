package training.six.e;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static char[][] field;
    private static Point initialPoint;


    static class Point {

        Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        Point(int i, int j, Point prevPoint) {
            this.i = i;
            this.j = j;
            this.prevPoint = prevPoint;
        }

        int i, j;
        Point prevPoint;
    }

    public static void main(String[] args) throws IOException {
        fillField();
        findInitialAndFinalPoint();
        findPath();
    }

    private static void findInitialAndFinalPoint() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == '@') {
                    initialPoint = new Point(i, j);
                }
            }
        }
    }

    private static Point[][] prevPoints;

    private static void findPath() {
        Deque<Point> nextSteps = new ArrayDeque<>();
        nextSteps.addLast(initialPoint);
        prevPoints[initialPoint.i][initialPoint.j] = initialPoint;
        Point finalPoint = null;
        while (!nextSteps.isEmpty()) {
            Point currentPoint = nextSteps.pollLast();
            prevPoints[currentPoint.i][currentPoint.j] = currentPoint;
            if (field[currentPoint.i][currentPoint.j] == 'X') {
                finalPoint = currentPoint;
                break;
            }
            List<Point> nextPoints = getNextPoints(currentPoint);
            nextSteps.addAll(nextPoints);
        }
        if (finalPoint != null) {
            Point prevPoint = finalPoint.prevPoint;
            System.out.println("Optimal path:");
            while (prevPoint != null) {
                field[prevPoint.i][prevPoint.j] = '+';
                prevPoint = prevPoint.prevPoint;
            }
            for (char[] row : field) {
                System.out.println(Arrays.toString(row));
            }
        } else {
            System.out.println("N");
        }

    }

    private static List<Point> getNextPoints(Point currentPoint) {
        List<Point> points = new ArrayList<>();
        Point upPoint = getUpPoint(currentPoint);
        if (upPoint != null && prevPoints[upPoint.i][upPoint.j] == null) {
            points.add(upPoint);
            prevPoints[upPoint.i][upPoint.j] = upPoint;
        }
        Point downPoint = getDownPoint(currentPoint);
        if (downPoint != null && prevPoints[downPoint.i][downPoint.j] == null) {
            points.add(downPoint);
            prevPoints[downPoint.i][downPoint.j] = downPoint;
        }
        Point leftPoint = getLeftPoint(currentPoint);
        if (leftPoint != null && prevPoints[leftPoint.i][leftPoint.j] == null) {
            points.add(leftPoint);
            prevPoints[leftPoint.i][leftPoint.j] = leftPoint;
        }
        Point rightPoint = getRightPoint(currentPoint);
        if (rightPoint != null && prevPoints[rightPoint.i][rightPoint.j] == null) {
            points.add(rightPoint);
            prevPoints[rightPoint.i][rightPoint.j] = rightPoint;
        }
        return points;

    }

    private static Point getUpPoint(Point currentPoint) {
        if (currentPoint.i - 1 >= 0) {
            if (field[currentPoint.i - 1][currentPoint.j] != 'O') {
                return new Point(currentPoint.i - 1, currentPoint.j, currentPoint);
            }
        }
        return null;
    }

    private static Point getDownPoint(Point currentPoint) {
        if (currentPoint.i + 1 < field.length) {
            if (field[currentPoint.i + 1][currentPoint.j] != 'O') {
                return new Point(currentPoint.i + 1, currentPoint.j, currentPoint);
            }
        }
        return null;
    }

    private static Point getLeftPoint(Point currentPoint) {
        if (currentPoint.j - 1 >= 0) {
            if (field[currentPoint.i][currentPoint.j - 1] != 'O') {
                return new Point(currentPoint.i, currentPoint.j - 1, currentPoint);
            }
        }
        return null;
    }

    private static Point getRightPoint(Point currentPoint) {
        if (currentPoint.j + 1 < field.length) {
            if (field[currentPoint.i][currentPoint.j + 1] != 'O') {
                return new Point(currentPoint.i, currentPoint.j + 1, currentPoint);
            }
        }
        return null;
    }

    private static void fillField() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/six_e.input");
        List<String> data = downloader.download(path);
        int n = Integer.parseInt(data.get(0));
        field = new char[n][];
        for (int i = 1; i < data.size(); i++) {
            String line = data.get(i);
            field[i - 1] = line.toCharArray();
        }
        prevPoints = new Point[field.length][field.length];
    }

}
