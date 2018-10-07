package training.four.f;

import training.Downloader;
import training.IDownloader;
import training.ISaver;
import training.Saver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/four_f.input");
        List<String> input = downloader.download(path);
        char[] brackets = input.get(0).toCharArray();
        boolean isPassed = checkBrackets(brackets);
        Path pathOut = Paths.get("src/main/resources/four_f.output");
        ISaver saver = new Saver();
        saver.save(pathOut, Collections.singletonList(String.valueOf(isPassed)));
    }

    private static boolean checkBrackets(char[] brackets) {
        if (brackets.length % 2 != 0) {
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < brackets.length; i++) {
            char bracket = brackets[i];
            if ((bracket == '{' || bracket == '[' || bracket == '(')) {
                if (stack.size() >= brackets.length / 2) {
                    return false;
                }
                stack.addLast(bracket);
                continue;
            }
            char prevBracket = stack.getLast();
            if (checkBracket(prevBracket, bracket)) {
                stack.removeLast();
            } else {
                return false;
            }
        }
        return stack.size() == 0;

    }

    private static boolean checkBracket(char prevBracket, char bracket) {
        if (prevBracket == '(' && bracket == ')') {
            return true;
        } else if (prevBracket == '[' && bracket == ']') {
            return true;
        } else return prevBracket == '{' && bracket == '}';
    }

}
