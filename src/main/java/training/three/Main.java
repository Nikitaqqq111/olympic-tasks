package training.three;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/three_c.input");
        List<String> data = downloader.download(path);
        String[] moneyBox = data.get(0).split(" ");
        int weight = Integer.parseInt(moneyBox[1]) - Integer.parseInt(moneyBox[0]);
        int[] weights = new int[data.size() - 2];
        int[] prices = new int[data.size() - 2];
        for (int i = 2; i < data.size(); i++) {
            prices[i - 2] = Integer.parseInt(data.get(i).split(" ")[0]);
            weights[i - 2] = Integer.parseInt(data.get(i).split(" ")[1]);
        }
        MoneyBox moneyBoxCalculator = new MoneyBox(weight, weights, prices);
        int min = moneyBoxCalculator.calculateMin();
        int max = moneyBoxCalculator.calculateMax();
        String answer;
        if (min == -1) {
            answer = "This is impossible";
        } else {
            answer = String.valueOf(min) + " " + String.valueOf(max);
        }
        Path pathOut = Paths.get("src/main/resources/three_c.output");
        ISaver saver = new Saver();
        saver.save(pathOut, Collections.singletonList(answer));

    }

}
