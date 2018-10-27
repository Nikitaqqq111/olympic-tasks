package training.eight;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        int[] array = fillArray();
        quickSort(array);
        int s = 0;
        for (int anArray : array) {
            if (anArray <= s + 1) {
                s += anArray;
            } else {
                break;
            }
        }
        System.out.println(s + 1);
    }

    private static void quickSort(int[] array) {
        quickSort(0, array.length, array);
    }

    private static int findPosition(int left, int right, int[] array) {
        int indexMain = left + (right - left) / 2;
        int countLessElements = 0;
        for (int i = left; i < right; i++) {
            if (array[i] < array[indexMain]) {
                countLessElements++;
            }
        }
        int tmp = array[left + countLessElements];
        array[left + countLessElements] = array[indexMain];
        array[indexMain] = tmp;
        return left + countLessElements;
    }

    private static void quickSort(int left, int right, int[] array) {
        if (left >= right) {
            return;
        }
        int position = findPosition(left, right, array);
        int righIndexViewed = position;
        for (int i = left; i < position; i++) {
            if (array[i] < array[position]) {
                continue;
            }
            for (int j = righIndexViewed; j < right; j++) {
                if (array[j] >= array[position]) {
                    continue;
                }
                int tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                righIndexViewed = j + 1;
                break;
            }
        }
        quickSort(left, position, array);
        quickSort(position + 1, right, array);
    }

    private static int[] fillArray() throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/eight_a.input");
        List<String> data = downloader.download(path);
        int[] array = new int[data.size() - 1];
        for (int i = 1; i < data.size(); i++) {
            array[i - 1] = Integer.parseInt(data.get(i));
        }
        return array;
    }

}
