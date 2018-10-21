package training.seven.a;

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
        Path path = Paths.get("src/main/resources/seven_a.input");
        int n = Integer.parseInt(downloader.download(path).get(0));
        calculateAllFractions(n);
        fractions.sort((fraction1, fraction2) -> fraction1.i * fraction2.j - fraction2.i * fraction1.j);
        fractions.forEach(System.out::println);
    }

    private static List<Fraction> fractions = new ArrayList<>();

    static class Fraction {
        Fraction(int i, int j) {
            this.i = i;
            this.j = j;
        }

        private final int i, j;

        @Override
        public String toString() {
            return i + "/" + j;
        }


    }

    private static void calculateAllFractions(int n) {
        for (int i = 1; i < n; i ++) {
            for (int j = i + 1; j <= n; j++) {
                if (getNOD(j, i) == 1) {
                    fractions.add(new Fraction(i, j));
                }
            }
        }
    }

    private static int getNOD(int i, int j) {
        if (j == 0) {
            return i;
        }
        return getNOD(j, i % j);
    }

}
