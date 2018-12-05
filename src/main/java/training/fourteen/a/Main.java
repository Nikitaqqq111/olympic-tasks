package training.fourteen.a;

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
        Path path = Paths.get("src/main/resources/fourteen_a.input");
        List<String> data = downloader.download(path);
        String initialString = data.get(0);
        data.remove(0);
        List<String> leftParts = new ArrayList<>();
        List<String> rightParts = new ArrayList<>();
        for (String string : data) {
            String[] strings = string.split(" -> ");
            leftParts.add(strings[0]);
            rightParts.add(strings[1]);
        }
        System.out.println(calculateSequence(initialString, leftParts, rightParts));
    }

    private static MarkCycle calculateSequence(String initialString, List<String> leftParts, List<String> rightParts) {
        StringBuilder initialSb = new StringBuilder(initialString);
        List<String> seqStrings = new ArrayList<>();
        int count = 0;
        int indexOfFirstOccurrence = -1;
        boolean foundCycle = false;
        seqStrings.add(initialString);
        do {
            boolean foundOccurrence = false;
            count++;
            for (int i = 0; i < leftParts.size(); i++) {
                int indexOf = initialSb.indexOf(leftParts.get(i));
                if (indexOf == -1) {
                    continue;
                }
                foundOccurrence = true;
                initialSb.delete(indexOf, indexOf + leftParts.get(i).length());
                initialSb.insert(indexOf, rightParts.get(i));
                String snapshot = initialSb.toString();
                int indexOfSnapshot = seqStrings.indexOf(snapshot);
                if (indexOfSnapshot == -1) {
                    seqStrings.add(snapshot);
                } else {
                    indexOfFirstOccurrence = indexOfSnapshot;
                    foundCycle = true;
                }
                break;
            }
            if (!foundOccurrence) {
                return new MarkCycle(0, count);
            }
            if (foundCycle) {
                return new MarkCycle(count - indexOfFirstOccurrence, indexOfFirstOccurrence);
            }
        } while (true);
    }

}

class MarkCycle {
    final int period;
    final int acyclicLength;

    MarkCycle(int period, int acyclicLength) {
        this.period = period;
        this.acyclicLength = acyclicLength;
    }

    @Override
    public String toString() {
        return "MarkCycle{" +
                "period=" + period +
                ", acyclicLength=" + acyclicLength +
                '}';
    }
}
