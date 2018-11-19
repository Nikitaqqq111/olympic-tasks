package training.eight.b;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final List<Button> sequence = new ArrayList<>();

    private enum Button {
        TWO(Arrays.asList('A', 'B', 'C')),
        THREE(Arrays.asList('D', 'E', 'F')),
        FOUR(Arrays.asList('G', 'H', 'I')),
        FIVE(Arrays.asList('J', 'K', 'L')),
        SIX(Arrays.asList('M', 'N', 'O')),
        SEVEN(Arrays.asList('P', 'Q', 'R', 'S')),
        EIGHT(Arrays.asList('T', 'U', 'V')),
        NINE(Arrays.asList('W', 'X', 'Y', 'Z'));

        private final List<Character> characters;

        Button(List<Character> characters) {
            this.characters = characters;
        }

        static Button getButtonForSymbol(char symbol) {
            for (Button button : Button.values()) {
                for (Character character : button.characters) {
                    if (symbol == character) {
                        return button;
                    }
                }
            }
            throw new IllegalArgumentException("WTF");
        }

        int getIndexFor(char symbol) {
            return characters.indexOf(symbol) + 1;
        }

    }

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/eight_b.input");
        List<String> data = downloader.download(path);
        int n = Integer.parseInt(data.get(0));
        char[] chars = data.get(1).toCharArray();
        fillSequence(chars);
        System.out.println(calculateCountOfSequences(n));
    }

    private static long calculateCountOfSequences(int n) {
        long[][] cache = new long[2][sequence.size() + 1];
        cache[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            cache[i % 2][0] = 0;
            for (int j = 1; j < cache[0].length; j++) {
                Button currentButton = sequence.get(j - 1);
                int count = 0;
                int jj = j - 1;
                while (jj >= 0 && count <= 4 && currentButton == sequence.get(jj)) {
                    count++;
                    jj--;
                }
                cache[i % 2][j] = 0;
                if (currentButton == Button.SEVEN || currentButton == Button.NINE) {
                    for (int ii = 1; ii <= count; ii++) {
                        cache[i % 2][j] = cache[i % 2][j] + cache[(i - 1) % 2][j - ii];
                    }
                } else {
                    for (int ii = 1; ii <= Math.min(count, 3); ii++) {
                        cache[i % 2][j] = cache[i % 2][j] + cache[(i - 1) % 2][j - ii];
                    }
                }
            }
        }
        return cache[n % 2][sequence.size()];
    }

    private static void fillSequence(char[] chars) {
        for (char aChar : chars) {
            Button button = Button.getButtonForSymbol(aChar);
            for (int j = 1; j <= button.getIndexFor(aChar); j++) {
                sequence.add(button);
            }
        }
    }

}
