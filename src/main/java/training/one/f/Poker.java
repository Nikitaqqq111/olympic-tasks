package training.one.f;

import java.util.Collections;
import java.util.List;

class Poker {

    static String calculateSecondVariation(List<Integer> cards) {
        int[] counts = new int[13];
        for (Integer card : cards) {
            counts[card - 1]++;
        }
        int[] countsSecondLevel = new int[5];
        for (int i = 0; i < counts.length; i++) {
            countsSecondLevel[counts[i] - 1]++;
        }

        if (countsSecondLevel[4] == 1) {
            return "Impossible";
        }
        if (countsSecondLevel[3] == 1) {
            return "Four of a Kind";
        }
        if (countsSecondLevel[2] == 1 && countsSecondLevel[1] == 2) {
            return "Full House";
        }

        if (countsSecondLevel[2] == 1) {
            return "Three of a Kind";
        }
        if (counts[1] == 2) {
            return "Two Pairs";
        }
        if (counts[1] == 1) {
            return "One Pair";
        }

        if (checkStraight(cards)) {
            return "Straight";
        }

        return "Nothing";
    }

    static String calculate(List<Integer> cards) {
        if (checkStraight(cards)) {
            return "Straight";
        }
        int[] counts = new int[13];
        for (Integer card : cards) {
            counts[card - 1]++;
        }
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts.length - i - 1; j++) {
                if (counts[j] < counts[j + 1]) {
                    int swap = counts[j];
                    counts[j] = counts[j + 1];
                    counts[j + 1] = swap;
                }
            }
        }

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 5) {
                return "Impossible";
            }
            if (counts[i] == 4) {
                return "Four of a Kind";
            }
            if (counts[i] == 3 && counts[i + 1] == 2) {
                return "Full House";
            }

            if (counts[i] == 3) {
                return "Three of a Kind";
            }
            if (counts[i] == 2 && counts[i + 1] == 2) {
                return "Two Pairs";
            }
            if (counts[i] == 2) {
                return "One Pair";
            }
        }
        return "Nothing";
    }

    private static boolean checkStraight(List<Integer> cards) {
        Collections.sort(cards);
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i + 1) - cards.get(i) != 1) {
                return false;
            }
        }
        return true;
    }

}
