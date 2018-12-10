package training.fourteen.f;

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
        Path path = Paths.get("src/main/resources/fourteen_f.input");
        List<String> data = downloader.download(path);
        int n = Integer.parseInt(data.get(0));
        String[][] tableInput = new String[9][26];
        for (int i = 1; i < data.size(); i++){
            String[] line = data.get(i).replace(" ", "").split("=");
            int jj = line[0].charAt(0) - 'A';
            int ii = line[0].charAt(1) - '0' - 1;
            tableInput[ii][jj] = line[1];
        }
        Table table = new Table(tableInput);
        for (String[] row : tableInput) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println(table.calculate(0, 0));
    }

}
