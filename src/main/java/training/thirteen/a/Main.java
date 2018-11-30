package training.thirteen.a;

import training.Downloader;
import training.IDownloader;
import training.ten.a.SequenceUtils;
import training.ten.a.SortUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/thirteen_a.input");
        String[] data = downloader.download(path).get(0).split(" ");
        int x1 = Integer.parseInt(data[0]);
        int y1 = Integer.parseInt(data[1]);
        int x2 = Integer.parseInt(data[2]);
        int y2 = Integer.parseInt(data[3]);
        int d1 = Integer.parseInt(data[4]);
        int d2 = Integer.parseInt(data[5]);
        SortedSet<Integer> areas = calculateAreas(x1, y1, x2, y2, d1, d2);
        System.out.println(areas);
    }

    private static SortedSet<Integer> calculateAreas(int x1, int y1, int x2, int y2, int d1, int d2) {
        Collection<Integer> xLines = calculateLines(x1, x2, d1);
        Collection<Integer> yLines = calculateLines(y1, y2, d2);
        SortedSet<Integer> areas = new TreeSet<>();
        for (Integer xLine : xLines) {
            for (Integer yLine : yLines) {
                areas.add(xLine * yLine);
            }
        }
        return areas;
    }

    private static Collection<Integer> calculateLines(int x1, int x2, int d1) {
        Deque<Integer> offsets = new ArrayDeque<>();
        if (x1 > x2) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        offsets.add((d1 % x2) % x1);
        boolean isFullyIncluded = false;
        do {
            if (!isFullyIncluded) {
                isFullyIncluded = (x2 - offsets.getLast()) / x1 > 0;
            }
            int nextOffset = x1 - ((x2 - offsets.getLast()) % x1);
            if (offsets.contains(nextOffset)) {
                break;
            } else {
                offsets.addLast(nextOffset);
            }
        } while (true);
        if (isFullyIncluded) {
            offsets.addLast(x1);
        }
        offsets.removeIf(offset -> offset == 0);
        return offsets;
    }

}
