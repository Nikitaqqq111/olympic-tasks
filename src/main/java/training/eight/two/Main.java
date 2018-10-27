package training.eight.two;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {

    private enum Mobile {
        TWO(Arrays.asList('A', 'B', 'C')),
        THREE(Arrays.asList('D', 'E', 'F')),
        FOUR(Arrays.asList('G', 'H', 'I')),
        FIVE(Arrays.asList('J', 'K', 'L')),
        SIX(Arrays.asList('M', 'N', 'O')),
        SEVEN(Arrays.asList('P', 'Q', 'R', 'S')),
        EIGHT(Arrays.asList('T', 'U', 'V')),
        NINE(Arrays.asList('W', 'X', 'Y', 'Z'));

        private final List<Character> characters;

        Mobile(List<Character> characters) {
            this.characters = characters;
        }

        static Mobile getButtonForSymbol(char symbol) {
            for (Mobile button : Mobile.values()) {
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

        boolean inButton(char symbol) {
            return characters.indexOf(symbol) != -1;
        }
    }

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/eight_b.input");
        List<String> data = downloader.download(path);
        int n = Integer.parseInt(data.get(0));
        char[] chars = data.get(1).toCharArray();
        System.out.println("Count of input sequences: " + calculateCountOfInputSequences(n, chars));
    }

    private static int calculateCountOfInputSequences(int n, char[] chars) {
        int[][] table = new int[n + 1][chars.length + 1];
        table[0][0] = 1;
        for (int i = 1; i < table.length; i++) {
            for (int j = 1; j < table[0].length; j++) {
                char symbol = chars[j - 1];
                Mobile button = Mobile.getButtonForSymbol(symbol);
                int index = button.getIndexFor(symbol);
                for (int prev = 1; prev <= index; prev++) {
                    if (i - prev < 0) {
                        continue;
                    }
                    table[i][j] += table[i - prev][j - 1];
                }
                int k = 1;
                int jj = j - 2;
                while (jj > -1 && button.inButton(chars[jj])
                        && index + button.getIndexFor(chars[jj]) <= button.characters.size()) {
                    index += button.getIndexFor(chars[jj--]);
                    if (j - ++k > -1) {
                        table[i][j] += table[i - 1][j - k];
                    } else {
                        break;
                    }
                }

            }
        }
        for (int[] aTable : table) {
            System.out.println(Arrays.toString(aTable));
        }
        return table[n][chars.length];
    }
}
