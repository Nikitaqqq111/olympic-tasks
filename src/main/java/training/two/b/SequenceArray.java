package training.two.b;

import java.util.*;

class SequenceArray {

    static List<List<Character>> getAllReShuffle(char[] sequence) {
        Set<Character> availableCharacters = new HashSet<>();
        for (char symbol : sequence) {
            availableCharacters.add(symbol);
        }
        return shuffle(availableCharacters);
    }

    private static List<List<Character>> shuffle(Set<Character> availableCharacters) {
        if (availableCharacters.size() == 1) {
            List<Character> row = new ArrayList<>();
            row.add(availableCharacters.iterator().next());
            List<List<Character>> table = new ArrayList<>();
            table.add(row);
            return table;
        }
        List<List<Character>> table = new ArrayList<>();
        for (Character character : availableCharacters) {
            Set<Character> availableCharactersOnNextLevel = new HashSet<>(availableCharacters);
            availableCharactersOnNextLevel.remove(character);
            List<List<Character>> shuffles = shuffle(availableCharactersOnNextLevel);
            for (List<Character> rows : shuffles) {
                rows.add(character);
            }
            table.addAll(shuffles);
        }
        return table;
    }

    private static final boolean[] isUsedArray = new boolean[8];
    static int count = 0;

    static void shuffle(char[] sequence, int currentIndex, char[] resultSequence) {
        if (currentIndex == resultSequence.length) {
            System.out.println(Arrays.toString(resultSequence));
            count++;
        }
        for (int i = 0; i < sequence.length; i++) {
            if (!isUsedArray[i]) {
                resultSequence[currentIndex] = sequence[i];
                isUsedArray[i] = true;
                shuffle(sequence, currentIndex + 1, resultSequence);
                isUsedArray[i] = false;
            }

        }

    }

}
