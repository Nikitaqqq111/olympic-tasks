package training.twelve.f;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/twelve_f.input");
        List<String> data = downloader.download(path);
        Formula initialFormula = new Formula(data.get(0));
        System.out.println(initialFormula.formula());
        for (int i = 2; i < data.size(); i++) {
            System.out.println(new Formula(data.get(i)).formula());
        }
    }

}
