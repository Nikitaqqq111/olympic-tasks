package training.one.e;

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
        Path path = Paths.get("src/main/resources/one_e.input");
        String input = downloader.download(path).get(0);
        int a = Integer.parseInt(input.split(" ")[0]);
        int n = Integer.parseInt(input.split(" ")[1]);
        Integer[] aDigits = Multiplier.convertToArray(a);
        Integer[] result = Pow.pow(aDigits, n);
        StringBuilder sb = new StringBuilder();
        List<Integer> resultList = new ArrayList<>(Arrays.asList(result));
        Collections.reverse(resultList);
        boolean isFindNonZero = false;
        Iterator<Integer> iterator = resultList.iterator();
        while (iterator.hasNext()) {
            Integer readNumber = iterator.next();
            if (!isFindNonZero && readNumber == 0) {
                iterator.remove();
            }
            if (readNumber != 0) {
                isFindNonZero = true;
            }
        }
        resultList.forEach(sb::append);
        Path pathOut = Paths.get("src/main/resources/one_e.output");
        ISaver saver = new Saver();
        saver.save(pathOut, Collections.singletonList(sb.toString()));
    }

}
