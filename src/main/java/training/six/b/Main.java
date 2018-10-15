package training.six.b;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/six_b.input");
        String[] numbersAsString = downloader.download(path).get(1).split(" ");
        List<Integer> numbers = new ArrayList<>();
        for (String number: numbersAsString) {
            numbers.add(Integer.parseInt(number));
        }
        int countSum = calculateCountOfSum(numbers);
        System.out.println("Count of sum: " + countSum);
    }

    private static int calculateCountOfSum(List<Integer> numbers) {
        int maxSum = 0;
        for (Integer number : numbers) {
            maxSum += number;
        }
        boolean[] cache = new boolean[maxSum + 1];
        cache[0] = true;
        for (Integer number : numbers) {
            for (int i = cache.length - 1; i >= 0; i--) {
                if (i - number < 0) {
                    continue;
                }
                cache[i] = cache[i] || cache[i - number];
            }
        }
        int countSum = 0;
        for (boolean aCache : cache) {
            if (aCache) {
                countSum++;
            }
        }
        return countSum;
    }

}
