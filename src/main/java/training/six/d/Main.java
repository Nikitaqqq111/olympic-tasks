package training.six.d;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static class Rectangle {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        private Rectangle(int x1, int y1, int x2, int y2) {
            if (x1 < x2) {
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;
            } else {
                this.x1 = x2;
                this.y1 = y2;
                this.x2 = x1;
                this.y2 = y1;
            }
            if (y1 > y2) {
                int tmp = this.y1;
                this.y1 = this.y2;
                this.y2 = tmp;
            }
        }

        boolean isBelongs(double x, double y) {
            return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2;
        }

    }

    private static List<Rectangle> rectangles = new ArrayList<>();
    private static List<Integer> xLines = new ArrayList<>();
    private static List<Integer> yLines = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        fillRectangles();
        int area = calculateArea();
        System.out.println("Area: " + area);
    }

    private static int calculateArea() {
        Collections.sort(xLines);
        Collections.sort(yLines);
        int commonArea = 0;
        int i = 0;
        while (i < xLines.size() - 1) {
            int x1 = xLines.get(i);
            int x2 = xLines.get(i + 1);
            if (x2 - x1 == 0) {
                i++;
                continue;
            }
            int j = 0;
            while (j < yLines.size() - 1) {
                int y1 = yLines.get(j);
                int y2 = yLines.get(j + 1);
                if (y2 - y1 == 0) {
                    j++;
                    continue;
                }
                boolean isBelongsToRectangle = false;
                for (Rectangle rectangle : rectangles) {
                    if (rectangle.isBelongs(((double) x2 + x1) / 2, ((double) y2 + y1) / 2)) {
                        isBelongsToRectangle = true;
                        break;
                    }
                }
                if (isBelongsToRectangle) {
                    commonArea += (y2 - y1) * (x2 - x1);
                }
                j++;
            }
            i++;
        }
        return commonArea;
    }

    private static void fillRectangles() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/six_d.input");
        List<String> data = downloader.download(path);
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i).split(" ");
            rectangles.add(new Rectangle(Integer.parseInt(row[0]),
                    Integer.parseInt(row[1]),
                    Integer.parseInt(row[2]),
                    Integer.parseInt(row[3])));
            xLines.add(Integer.parseInt(row[0]));
            yLines.add(Integer.parseInt(row[1]));
            xLines.add(Integer.parseInt(row[2]));
            yLines.add(Integer.parseInt(row[3]));
        }
    }

}
