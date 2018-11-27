package training.twelve.e;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/twelve_e.input");
        List<String> data = Arrays.asList(downloader.download(path).get(0).split(" "));
        Point initialPoint = new Point(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(1)));
        Point goalPoint = new Point(Integer.parseInt(data.get(2)), Integer.parseInt(data.get(3)));
        int k = Integer.parseInt(data.get(4));
        List<Integer> lengths = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        for (int i = 5; i <= 5 + k - 1; i++) {
            lengths.add(Integer.parseInt(data.get(i)));
            counts.add(Integer.parseInt(data.get(i + k)));
        }
        int pipeCount = calculatePipeCount(initialPoint, goalPoint, 0, lengths, counts, new int[counts.size()]);
        System.out.println("pipeCount: " + (pipeCount == Integer.MAX_VALUE ? -1 : pipeCount));
    }

    private static int calculatePipeCount(Point initialPoint, Point goalPoint, int k, List<Integer> lengths, List<Integer> counts, int[] usedCounts) {
        if (k == lengths.size() - 1
                && Math.abs(initialPoint.x - goalPoint.x) % lengths.get(k) == 0
                && Math.abs(initialPoint.y - goalPoint.y) % lengths.get(k) == 0
                && Math.abs(initialPoint.x - goalPoint.x) / lengths.get(k) <= counts.get(k)
                && Math.abs(initialPoint.y - goalPoint.y) / lengths.get(k) <= counts.get(k) - Math.abs(initialPoint.x - goalPoint.x) / lengths.get(k)) {
            int sum = 0;
            for (int usedCount : usedCounts) {
                sum += usedCount;
            }
            return sum + Math.abs(initialPoint.x - goalPoint.x) / lengths.get(k) + Math.abs(initialPoint.y - goalPoint.y) / lengths.get(k);
        } else if (k == lengths.size() - 1) {
            return Integer.MAX_VALUE;
        }

        int minPipeCount = Integer.MAX_VALUE;
        for (int i = -counts.get(k); i <= counts.get(k); i++) {
            for (int j = -counts.get(k); j <= counts.get(k); j++) {
                if (Math.abs(i) + Math.abs(j) <= counts.get(k)) {
                    Point nextPoint = new Point(initialPoint.x + i * lengths.get(k), initialPoint.y + j * lengths.get(k));
                    usedCounts[k] += (Math.abs(i) + Math.abs(j));
                    int pipeCount = calculatePipeCount(nextPoint, goalPoint, k + 1, lengths, counts, usedCounts);
                    if (pipeCount < minPipeCount) {
                        minPipeCount = pipeCount;
                    }
                    usedCounts[k] -= (Math.abs(i) + Math.abs(j));
                }
            }
        }
        return minPipeCount;
    }
}

class Point {
    final int x;
    final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
