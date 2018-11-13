package training.two.b.algo_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceUtils {

    private static final List<String> sequences = new ArrayList<>();

    public static List<String> generateAllSequences(char[] symbols) {
        Arrays.sort(symbols);
        sequences.add(new String(symbols));
        generateNextSequence(symbols);
        return sequences;
    }

    private static void generateNextSequence(char[] symbols) {
        int i = symbols.length - 1;
        while (i > 0) {
            if (symbols[i] > symbols[i - 1]) {
                break;
            }
            i--;
        }
        if (i == 0) {
            return;
        }
        char charGoal = symbols[i - 1];
        char minChar = symbols[i];
        int indexMinChar = i;
        for (int j = i; j < symbols.length; j++) {
            if (symbols[j] > charGoal && symbols[j] < minChar) {
                minChar = symbols[j];
                indexMinChar = j;
            }
        }
        char swap = symbols[indexMinChar];
        symbols[indexMinChar] = symbols[i - 1];
        symbols[i - 1] = swap;
        Arrays.sort(symbols, i, symbols.length);
        sequences.add(new String(symbols));
        generateNextSequence(symbols);
    }

}