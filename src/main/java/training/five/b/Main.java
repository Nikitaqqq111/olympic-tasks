package training.five.b;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static List<Character> openBrackets = new ArrayList<>();
    private static List<Character> closeBrackets = new ArrayList<>();

    static {
        openBrackets.add('(');
        openBrackets.add('[');
        closeBrackets.add(')');
        closeBrackets.add(']');
    }

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/five_b.input");
        int n = Integer.parseInt(downloader.download(path).get(0));
        generateBracketsSequence(n);
    }

    private static void generateBracketsSequence(int n) {
        char[] sequence = new char[n];
        generateBracketsSequence(sequence, 0, new ArrayDeque<>());
    }

    private static void generateBracketsSequence(char[] sequence, int index, Deque<Character> deque) {
        if (index == sequence.length) {
            System.out.println(Arrays.toString(sequence));
            return;
        }
        if (index == 0) {
            for (Character bracket : openBrackets) {
                deque.pollLast();
                sequence[index] = bracket;
                deque.addLast(bracket);
                generateBracketsSequence(sequence, index + 1, new ArrayDeque<>(deque));
            }
            return;
        }
        if (deque.size() == sequence.length - index) {
            Character prevBracket = deque.pollLast();
            if (prevBracket == openBrackets.get(0)) {
                sequence[index] = closeBrackets.get(0);
            } else {
                sequence[index] = closeBrackets.get(1);
            }
            generateBracketsSequence(sequence, index + 1, new ArrayDeque<>(deque));
        } else if (deque.isEmpty()) {
            for (Character bracket : openBrackets) {
                deque.pollLast();
                sequence[index] = bracket;
                deque.addLast(bracket);
                generateBracketsSequence(sequence, index + 1, new ArrayDeque<>(deque));
            }
        } else {
            Character prevCharacter = deque.pollLast();
            if (prevCharacter == openBrackets.get(0)) {
                sequence[index] = closeBrackets.get(0);
            } else {
                sequence[index] = closeBrackets.get(1);
            }
            generateBracketsSequence(sequence, index + 1, new ArrayDeque<>(deque));
            deque.addLast(prevCharacter);
            for (Character bracket : openBrackets) {
                sequence[index] = bracket;
                deque.addLast(bracket);
                generateBracketsSequence(sequence, index + 1, new ArrayDeque<>(deque));
                deque.removeLast();
            }
        }
    }
}
