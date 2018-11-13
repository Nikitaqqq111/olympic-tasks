package training.ten.a;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/ten_a.input");
        int n = Integer.parseInt(downloader.download(path).get(0));
        List<String> sequences = SequenceUtils.generateSequences(n);

        SortedMap<Integer, List<String>> map = new TreeMap<>();
        for (int i = 0; i < sequences.size(); i++) {
            String sequenceAsString = sequences.get(i);
            int[] array = new int[sequenceAsString.length()];
            for (int j = 0; j < sequenceAsString.length(); j++) {
                array[j] = Character.digit(sequenceAsString.charAt(j), 10);
            }
            SortUtils.quickSort(array, 0, array.length - 1);
            map.putIfAbsent(SortUtils.count, new ArrayList<>());
            map.get(SortUtils.count).add(sequenceAsString);
            SortUtils.count = 0;
        }
        System.out.println(map.get(map.lastKey()));
        System.out.println(generateBadSequence(n));
    }

    static String generateBadSequence(int n) {
        if (n == 1) {
            return "1";
        }
        if (n == 2) {
            return "12";
        }
        int[] array = new int[n];
        int pivotIndex = (n - 1) / 2;
        int k, left, right;
        if (n % 2 == 1) {
            array[pivotIndex] = 1;
            k = 0;
            left = n;
            right = 2;
        } else {
            array[pivotIndex] = n;
            k = 0;
            left = n - 1;
            right = 1;
        }
        while (k < pivotIndex - 1) {
            array[k] = left;
            array[n - k - 1] = right;
            left--;
            right++;
            k++;
        }
        array[k] = right;
        array[n - k - 1] = left--;
        if (n % 2 == 0) {
            array[pivotIndex + 1] = left;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
        }
        return sb.toString();
    }

}
