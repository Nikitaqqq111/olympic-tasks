package training.nine.one;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

public class Main {

    private static int countOfMessages;

    private static class File {
        private final String name;
        private final String extension;
        private final String fullName;
        private final int number;

        private File(String name, String extension, int number, String fullName) {
            this.name = name;
            this.extension = extension;
            this.number = number;
            this.fullName = fullName;
        }

        @Override
        public String toString() {
            return "File{" +
                    "name='" + name + '\'' +
                    ", extension='" + extension + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", number=" + number +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        File[] files = getFiles();
        ArraysUtils arraysUtils = new ArraysUtils();
        int right = files.length - 1;
        for (int i = 1; i <= countOfMessages; i++) {
            if (i % 2 == 1) {
                arraysUtils.sort(files, right, Comparator.comparing(f -> f.extension));
                right = arraysUtils.deleteUniqueElements(files, right, Comparator.comparing(f -> f.extension));
            } else {
                arraysUtils.sort(files, right, Comparator.comparing(f -> f.name));
                right = arraysUtils.deleteUniqueElements(files, right, Comparator.comparing(f -> f.name));
            }
        }
        arraysUtils.sort(files, right, Comparator.comparing(f -> f.number));
        for (int i = 0; i <= right; i++) {
            System.out.println(files[i].fullName);
        }
    }


    private static File[] getFiles() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/nine_a.input");
        List<String> data = downloader.download(path);
        File[] files = new File[Integer.parseInt(data.get(0).split(" ")[0])];
        countOfMessages = Integer.parseInt(data.get(0).split(" ")[1]);
        for (int i = 1; i < data.size(); i++) {
            String[] fileAsStringArray = data.get(i).split("\\.");
            File file;
            if (fileAsStringArray.length == 2) {
                file = new File(fileAsStringArray[0], fileAsStringArray[1], i - 1, data.get(i));
            } else if (fileAsStringArray.length == 1) {
                file = new File(fileAsStringArray[0], "", i - 1, data.get(i));
            } else {
                file = new File(data.get(i), "", i - 1, data.get(i));
            }
            files[i - 1] = file;
        }
        return files;
    }

}
