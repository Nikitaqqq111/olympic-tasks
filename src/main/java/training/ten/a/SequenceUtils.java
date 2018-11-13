package training.ten.a;

import java.io.IOException;
import java.util.List;

public class SequenceUtils {

    static List<String> generateSequences(int n) throws IOException {
        char[] sequence = new char[n];
        for (int i = 1; i <= n; i++) {
            sequence[i - 1] = Character.forDigit(i, 10);
        }
        List<String> sequences = training.two.b.algo_2.SequenceUtils.generateAllSequences(sequence);
        return sequences;
    }

}
