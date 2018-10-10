package training.five.d;

import training.Downloader;
import training.IDownloader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static class Point {
        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final List<Point> points = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        fillData();
        Point firstOptimalPoint = findLeftDownPoint();
        points.sort((point1, point2) -> -(point1.x - firstOptimalPoint.x) * (point2.y - firstOptimalPoint.y)
                + (point1.y - firstOptimalPoint.y) * (point2.x - firstOptimalPoint.x));
        List<Point> optimalPoints = new ArrayList<>();
        optimalPoints.add(firstOptimalPoint);
        optimalPoints.add(points.get(0));
        int index = 1;
        while (index < points.size()) {
            Point prevOptimalPoint = optimalPoints.get(optimalPoints.size() - 1);
            Point prevOfPrevOptimalPoint = optimalPoints.get(optimalPoints.size() - 2);
            Point currentPoint = points.get(index);
            int angle = (prevOptimalPoint.x - prevOfPrevOptimalPoint.x) * (currentPoint.y - prevOfPrevOptimalPoint.y)
                - (prevOptimalPoint.y - prevOfPrevOptimalPoint.y) * (currentPoint.x - prevOfPrevOptimalPoint.x);
            if (angle > 0) {
                optimalPoints.add(currentPoint);
            } else {
                optimalPoints.remove(optimalPoints.size() - 1);
                optimalPoints.add(currentPoint);
            }
            index++;
        }
        double p = 0;
        for (int i = 0; i < optimalPoints.size() - 1; i++) {
            p += getPerimetr(optimalPoints.get(i), optimalPoints.get(i + 1));
        }
        p+= getPerimetr(optimalPoints.get(0), optimalPoints.get(optimalPoints.size() - 1));
        System.out.println("Optimal p: " + p);
    }

    private static double getPerimetr(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) +
                Math.pow(point1.y - point2.y, 2));
    }

    private static Point findLeftDownPoint() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        Point leftDownPoint = null;
        int indexOfLeftDownPoint = -1;
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            if (point.y < minY && point.x < minX) {
                leftDownPoint = point;
                minX = point.x;
                minY = point.y;
                indexOfLeftDownPoint = i;
            }
        }
        if (leftDownPoint == null) {
            throw new IllegalArgumentException("WTF?");
        }
        points.remove(indexOfLeftDownPoint);
        return leftDownPoint;
    }

    private static void fillData() throws Exception {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/five_d.input");
        List<String> pointsAsString = downloader.download(path);
        for (int i = 1; i < pointsAsString.size(); i++) {
            String pointAsString = pointsAsString.get(i);
            String[] xAndY = pointAsString.split(" ");
            Point point = new Point(Integer.parseInt(xAndY[0]), Integer.parseInt(xAndY[1]));
            points.add(point);
        }
    }
}
