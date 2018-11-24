package training.twelve.a;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/twelve_a.input");
        List<String> data = downloader.download(path);
        double n = Double.parseDouble(data.get(1));
        String[] sequence = data.get(0).split(" ");
        int d1 = Integer.parseInt(sequence[0]);
        int d2 = Integer.parseInt(sequence[1]);
        int d3 = Integer.parseInt(sequence[2]);
        System.out.println(calculateN(n, d1, d2, d3));
    }

    private static int calculateN(double n, int d1, int d2, int d3) {
        List<Integer> numbers = new LinkedList<>(Arrays.asList(d1, d2, d3));
        if (n <= 1001) {
            for (int i = 4; i <= n; i++) {
                numbers.add(numbers.stream().mapToInt(number -> number).sum() % 10);
                numbers.remove(0);
            }
            return numbers.get(numbers.size() - 1);
        }
        for (int i = 4; i <= 1001; i++) {
            numbers.add(numbers.stream().mapToInt(number -> number).sum() % 10);
            numbers.remove(0);
        }
        List<Integer> goalSequence = new LinkedList<>(numbers);
        int k = 1001;
        do {
            k++;
            numbers.add(numbers.stream().mapToInt(number -> number).sum() % 10);
            numbers.remove(0);
        } while (!numbers.equals(goalSequence));
        double q = (n - 1001) % (k - 1001);
        for (int i = 1; i <= q; i++) {
            goalSequence.add(numbers.stream().mapToInt(number -> number).sum() % 10);
            goalSequence.remove(0);
        }
        return goalSequence.get(goalSequence.size() - 1);
    }

}
