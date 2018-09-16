package training.two.b.algo_2;

import java.util.Arrays;

public class SequenceUtils {

    static int count = 0;

    public static void generateAllSequences(char[] symbols) {
        Arrays.sort(symbols);
        System.out.println(symbols);
        count++;
        generateNextSequence(symbols);
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
        System.out.println(symbols);
        count++;
        generateNextSequence(symbols);
    }

}