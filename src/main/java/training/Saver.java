package training;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Saver implements ISaver {

    @Override
    public void save(Path path, List<String> data) throws IOException {
        File file = new File(path.toAbsolutePath().toString());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String dataLine : data) {
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
