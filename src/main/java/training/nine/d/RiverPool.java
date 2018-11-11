package training.nine.d;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RiverPool {

    final int riverCount;
    final List<Integer> begins;
    final List<Integer> ends;
    final List<Double> xPoints;
    final List<Double> yPoints;
    int[] rivers;

    RiverPool(int riverCount, List<Integer> begins, List<Integer> ends, List<Double> xPoints, List<Double> yPoints) {
        this.riverCount = riverCount;
        this.begins = begins;
        this.ends = ends;
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }

    public static RiverPool createRiverPool() {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/nine_d_2.input");
        List<String> data = null;
        try {
            data = downloader.download(path);
        } catch (IOException e) {
            throw new RuntimeException("WTF?");
        }
        int riverCount = Integer.parseInt(data.get(0));
        int riverCountTmp = riverCount;
        data.remove(0);
        List<Integer> begins = new ArrayList<>();
        List<Integer> ends = new ArrayList<>();
        List<Double> xPoints = new ArrayList<>();
        List<Double> yPoints = new ArrayList<>();
        int riverIndex = 0;
        while (riverCountTmp > 0) {
            int pointsCount = Integer.parseInt(data.get(riverIndex));
            int pointIndex = riverIndex + 1;
            begins.add(xPoints.size());
            while (pointsCount-- > 0) {
                xPoints.add(Double.parseDouble(data.get(pointIndex).split(" ")[0]));
                yPoints.add(Double.parseDouble(data.get(pointIndex).split(" ")[1]));
                pointIndex++;
            }
            ends.add(xPoints.size() - 1);
            riverIndex = pointIndex;
            riverCountTmp--;
        }
        RiverPool riverPool = new RiverPool(riverCount, begins, ends, xPoints, yPoints);
        riverPool.combinePools();
        return riverPool;
    }

    void combinePools() {
        int[] rivers = new int[riverCount];
        for (int i = 0; i < rivers.length; i++) {
            rivers[i] = i;
        }
        for (int i = 0; i < rivers.length; i++) {
            for (int j = 0; j < rivers.length; j++) {
                if (i == j) continue;
                if (flowsInto(i, j)) rivers[i] = j;
            }
        }
        boolean isUpdated = true;
        while (isUpdated) {
            isUpdated = false;
            for (int i = 0; i < rivers.length; i++) {
                int intoRiver = rivers[i];
                if (intoRiver != i && rivers[intoRiver] != intoRiver) {
                    isUpdated = true;
                    rivers[i] = rivers[intoRiver];
                }
            }
        }
        this.rivers = rivers;
    }

    double calculateMaxPool() {
        double maxSquare = -1;
        for (int i = 0; i < rivers.length; i++) {
            List<Point> points = new ArrayList<>();
            for (int j = 0; j < rivers.length; j++) {
                if (rivers[j] == i) {
                    for (int k = begins.get(j); k <= ends.get(j); k++) {
                        points.add(new Point(xPoints.get(k), yPoints.get(k)));
                    }
                }
            }
            if (points.isEmpty()) continue;
            double square = calculateSquare(points);
            if (square > maxSquare) {
                maxSquare = square;
            }
        }
        return maxSquare;
    }

    private double calculateSquare(List<Point> points) {
        Point firstOptimalPoint = findLeftDownPoint(points);
        points.sort((point1, point2) -> {
            double result = -(point1.x - firstOptimalPoint.x) * (point2.y - firstOptimalPoint.y)
                    + (point1.y - firstOptimalPoint.y) * (point2.x - firstOptimalPoint.x);
            if (result < 0) {
                return -1;
            } else if (result > 0) {
                return 1;
            } else {
                return 0;
            }
        });
        List<Point> optimalPoints = new ArrayList<>();
        optimalPoints.add(firstOptimalPoint);
        optimalPoints.add(points.get(0));
        int index = 1;
        while (index < points.size()) {
            Point prevOptimalPoint = optimalPoints.get(optimalPoints.size() - 1);
            Point prevOfPrevOptimalPoint = optimalPoints.get(optimalPoints.size() - 2);
            Point currentPoint = points.get(index);
            double angle = (prevOptimalPoint.x - prevOfPrevOptimalPoint.x) * (currentPoint.y - prevOfPrevOptimalPoint.y)
                    - (prevOptimalPoint.y - prevOfPrevOptimalPoint.y) * (currentPoint.x - prevOfPrevOptimalPoint.x);
            if (angle > 0) {
                optimalPoints.add(currentPoint);
            } else {
                optimalPoints.remove(optimalPoints.size() - 1);
                optimalPoints.add(currentPoint);
            }
            index++;
        }
        return calculateSquareFromOptimalPoints(optimalPoints);
    }

    private double calculateSquareFromOptimalPoints(List<Point> optimalPoints) {
        double square = 0;
        Point initialPoint = optimalPoints.get(0);
        for (int i = 1; i < optimalPoints.size() - 1; i++) {
            Point point2 = optimalPoints.get(i);
            Point point3 = optimalPoints.get(i + 1);
            square += (point2.x - initialPoint.x) * (point3.y - initialPoint.y) - (point3.x - initialPoint.x) * (point2.y - initialPoint.y);
        }
        return Math.abs(square / 2d);
    }

    private Point findLeftDownPoint(List<Point> points) {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
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

    private boolean flowsInto(int i, int j) {
        double x = xPoints.get(ends.get(i));
        double y = yPoints.get(ends.get(i));
        for (int k = begins.get(j); k <= ends.get(j); k++) {
            if (xPoints.get(k).equals(x) && yPoints.get(k).equals(y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "RiverPool{" +
                "riverCount=" + riverCount + "\n" +
                ", begins=" + begins + "\n" +
                ", ends=" + ends + "\n" +
                ", xPoints=" + xPoints + "\n" +
                ", yPoints=" + yPoints + "\n" +
                '}';
    }

}
