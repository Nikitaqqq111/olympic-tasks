package training.fourteen.e;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/fourteen_e.input");
        List<String> data = downloader.download(path);
        String[] firstLine = data.get(0).split(" ");
        data.remove(0);
        int w = Integer.parseInt(firstLine[1]);
        int h = Integer.parseInt(firstLine[2]);
        List<Point> points = new ArrayList<>();
        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();
        for (String tree : data) {
            int x = Integer.parseInt(tree.split(" ")[0]);
            int y = Integer.parseInt(tree.split(" ")[1]);
            points.add(new Point(x, y));
            xList.add(x);
            yList.add(y);
        }
        points.add(new Point(w, h));
        points.add(new Point(0, 0));
        xList.add(0);
        xList.add(w);
        yList.add(0);
        yList.add(h);
        Foursquare foursquare = calculateMaxFoursquare(points, xList, yList, w, h);
        System.out.println(foursquare);
    }

    private static Foursquare calculateMaxFoursquare(List<Point> points, List<Integer> xList, List<Integer> yList, int w, int h) {
        int maxLength = -1;
        int xOptIndex = -1;
        int yOptIndex = -1;
        for (int i = 0; i < xList.size(); i++) {
            for (int j = 0; j < yList.size(); j++) {
                int l = 0;
                int r = Math.min(w, h);
                int optLength = -1;
                while (l <= r) {
                    int mid = (l + r) / 2;
                    if (checkTrees(xList.get(i), yList.get(j), mid, points, w, h)) {
                        optLength = mid;
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                if (optLength > maxLength) {
                    maxLength = optLength;
                    xOptIndex = i;
                    yOptIndex = j;
                    System.out.println(i + " " + j + " " + maxLength);
                }
            }
        }
        return new Foursquare(new Point(xList.get(xOptIndex), yList.get(yOptIndex)), maxLength);
    }

    private static boolean checkTrees(int x, int y, int length, List<Point> points, int w, int h) {
        if (x + length > w || y + length > h) {
            return false;
        }
        for (Point point : points) {
            if (point.x > x && point.x < x + length && point.y > y && point.y < y + length) {
                return false;
            }
        }
        return true;
    }

}

class Foursquare {
    final Point leftdownPoint;
    final int length;

    Foursquare(Point leftdownPoint, int length) {
        this.leftdownPoint = leftdownPoint;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Foursquare{" +
                "leftdownPoint=" + leftdownPoint +
                ", length=" + length +
                '}';
    }
}

class Point {
    final int x;
    final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}


