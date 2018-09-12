package training;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Downloader implements IDownloader {
    @Override
    public List<String> download(Path path) throws IOException {
        if (path == null) throw new IllegalArgumentException("Path is null");
        File inputFile = new File(path.toAbsolutePath().toString());
        if (!inputFile.exists()) throw new IllegalArgumentException("File does not exist");
        List<String> data = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return data;
    }
}
