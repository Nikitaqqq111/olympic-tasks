package training.ten.b;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/ten_b.input");
        List<String> data = downloader.download(path);
        int n = Integer.parseInt(data.get(0));
        String string = data.get(1);
        System.out.println(getCount(n, string));
    }

    private static int getCount(int n, String string) {
        String f1 = "A", f2 = "B";
        int[] counts = new int[n];
        String[] begins = new String[n + 1], ends = new String[n + 1];
        begins[0] = f1;
        ends[0] = f1;
        begins[1] = f2;
        ends[1] = f2;
        if ("A".equals(string)) {
            counts[0] = 1;
        }
        if ("B".equals(string)) {
            counts[1] = 1;
        }
        for (int i = 2; i < n; i++) {
            if (begins[i - 2].length() > 25) {
                begins[i] = begins[i - 2];
            } else {
                begins[i] = begins[i - 2] + begins[i - 1];
            }
            if (ends[i - 1].length() > 25) {
                ends[i] = ends[i - 1];
            } else {
                ends[i] = ends[i - 2] + ends[i - 1];
            }
            counts[i] = counts[i - 2] + counts[i - 1] + getMiddleCount(ends[i - 2], begins[i - 1], string);
        }
        System.out.println(Arrays.toString(begins));
        System.out.println(Arrays.toString(ends));
        System.out.println(Arrays.toString(counts));
        return counts[n - 1];
    }

    private static int getMiddleCount(String end, String begin, String string) {
        if (string.length() == 1) {
            return 0;
        }
        if (begin.length() + end.length() < string.length()) {
            return 0;
        }
        String middle = end + begin;
        int count = 0;
        for (int i = end.length() - string.length() + 1; i < end.length(); i++) {
            if (i < 0 || i + string.length() > middle.length()) {
                continue;
            }
            if (middle.substring(i, i + string.length()).equals(string)) {
                count++;
            }
        }
        return count;
    }
}
