package training;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IDownloader {
    List<String> download(Path path) throws IOException;
}
