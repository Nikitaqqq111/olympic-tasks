package training.eleven.d;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/eleven_d.input");
        String[] data = downloader.download(path).get(0).split(" ");
        Circle firstCircle = new Circle(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]));
        Circle secondCircle = new Circle(Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));
        double area = calculateCommonArea(firstCircle, secondCircle);
        System.out.println(area);
    }

    private static double calculateCommonArea(Circle firstCircle, Circle secondCircle) {
        double d = Math.sqrt(Math.pow(firstCircle.x - secondCircle.x, 2) + Math.pow(firstCircle.y - secondCircle.y, 2));
        if (d <= Math.abs(firstCircle.r - secondCircle.r)) {
            return Math.pow(Math.min(firstCircle.r, secondCircle.r), 2) * Math.PI;
        }
        if (d >= firstCircle.r + secondCircle.r) {
            return 0;
        }
        double ae = (Math.pow(firstCircle.r, 2) - Math.pow(secondCircle.r, 2) + Math.pow(d, 2)) / (2d * d);
        double eb = d - ae;
        double ce = Math.sqrt(Math.pow(firstCircle.r, 2) - Math.pow(ae, 2));
        if (ae == 0d) {
            double sLeft = Math.pow(firstCircle.r, 2) * Math.PI / 2d;
            double angleCBD = 2 * (Math.atan(ce / eb) < 0 ? Math.atan(ce / eb) + Math.PI : Math.atan(ce / eb));
            double areaSector = angleCBD * Math.pow(secondCircle.r, 2) / 2d;
            double areaTriangle = Math.pow(secondCircle.r, 2) * Math.sin(angleCBD) / 2d;
            return sLeft + areaSector - areaTriangle;
        }
        if (eb == 0d) {
            double sRight = Math.pow(secondCircle.r, 2) * Math.PI / 2d;
            double angleCAD = 2 * (Math.atan(ce / ae) < 0 ? Math.atan(ce / ae) + Math.PI : Math.atan(ce / ae));
            double areaSector = angleCAD * Math.pow(firstCircle.r, 2) / 2d;
            double areaTriangle = Math.pow(firstCircle.r, 2) * Math.sin(angleCAD) / 2d;
            return sRight + areaSector - areaTriangle;
        }

        double angleCBD = 2 * (Math.atan(ce / eb) < 0 ? Math.atan(ce / eb) + Math.PI : Math.atan(ce / eb));
        double areaLeftSector = angleCBD * Math.pow(secondCircle.r, 2) / 2d;
        double areaLeftTriangle = Math.pow(secondCircle.r, 2) * Math.sin(angleCBD) / 2d;

        double angleCAD = 2 * (Math.atan(ce / ae) < 0 ? Math.atan(ce / ae) + Math.PI : Math.atan(ce / ae));
        double areaRightSector = angleCAD * Math.pow(firstCircle.r, 2) / 2d;
        double areaRightTriangle = Math.pow(firstCircle.r, 2) * Math.sin(angleCAD) / 2d;

        return areaLeftSector - areaLeftTriangle + areaRightSector - areaRightTriangle;
    }

}

class Circle {
    final double x, y, r;

    Circle(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
