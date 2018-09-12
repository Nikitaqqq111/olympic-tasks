package training.one.d;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/one_d.input");
        List<String> pointsString = downloader.download(path);
        List<Point> trianglePoints = new ArrayList<>();
        for (String point : pointsString) {
            trianglePoints.add(new Point(Integer.parseInt(point.split(" ")[0]),
                    Integer.parseInt(point.split(" ")[1])));
        }
        Point otherPoint = new Point(Integer.parseInt(pointsString.get(3).split(" ")[0]),
                Integer.parseInt(pointsString.get(3).split(" ")[1]));
        String answer = doesPointBelongs(trianglePoints, otherPoint) ? "In" : "Out";
        Path pathOut = Paths.get("src/main/resources/one_d.output");
        ISaver saver = new Saver();
        saver.save(pathOut, Collections.singletonList(answer));
    }

    private static boolean doesPointBelongs(List<Point> trianglePoints, Point otherPoint) {
        List<Factors> factors = new ArrayList<>();
        factors.add(Factors.calculateFactors(trianglePoints.get(0), trianglePoints.get(1)));
        factors.add(Factors.calculateFactors(trianglePoints.get(1), trianglePoints.get(2)));
        factors.add(Factors.calculateFactors(trianglePoints.get(0), trianglePoints.get(2)));
        Map<Factors, String> factorsSigns = new HashMap<>();
        factorsSigns.put(factors.get(0), greaterOrLower(factors.get(0), trianglePoints.get(2)));
        factorsSigns.put(factors.get(1), greaterOrLower(factors.get(1), trianglePoints.get(0)));
        factorsSigns.put(factors.get(2), greaterOrLower(factors.get(2), trianglePoints.get(1)));
        return doesPointBelongsSide(factorsSigns, otherPoint);
    }

    private static String greaterOrLower(Factors factor, Point otherPoint) {
        if (factor.particular) {
            if (otherPoint.x == -factor.b) {
                return "equal";
            }
            if (otherPoint.x > -factor.b) {
                return "greater";
            }
            return "lower";
        }
        if (otherPoint.y == factor.a * otherPoint.x + factor.b) {
            return "equal";
        }
        if (otherPoint.y > factor.a * otherPoint.x + factor.b) {
            return "greater";
        }
        return "lower";
    }

    private static boolean doesPointBelongsSide(Map<Factors, String> factorsSigns, Point otherPoint) {
        for (Map.Entry<Factors, String> entry : factorsSigns.entrySet()) {
            String greater = greaterOrLower(entry.getKey(), otherPoint);
            if (!greater.equals(entry.getValue()) && !greater.equals("equal")) {
                return false;
            }
        }
        return true;
    }

}
