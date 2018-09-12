package training;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ISaver {

    void save(Path path, List<String> data) throws IOException;

}
