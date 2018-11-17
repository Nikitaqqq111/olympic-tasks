package training.ten.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/ten_c.input");
        List<String> data = downloader.download(path);
        int step = Integer.parseInt(data.get(0).split(" ")[1]);
        String[] strings = data.get(1).split("X");
        List<Integer> strips = new ArrayList<>();
        for (String string : strings) {
            strips.add(string.length());
        }
        Game game = new Game(new Situation(strips), step);
        game.calculateAllSteps();
        int win = game.calculateWins(0);
        if (win == 0) {
            System.out.println(0);
        } else {
            System.out.println(win == 1 ? 1 : 2);
        }
    }

}
