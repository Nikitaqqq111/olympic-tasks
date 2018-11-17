package training.ten.c;

import java.util.*;

class Game {

    private final List<Situation> situations = new ArrayList<>();
    private final int step;

    Game(Situation initialSituation, int step) {
        this.step = step;
        if (normalize(initialSituation)) {
            situations.add(initialSituation);
        }
    }

    private boolean normalize(Situation situation) {
        situation.strips.removeIf(strip -> strip < step);
        situation.strips.sort(Comparator.reverseOrder());
        if (situation.isEmpty()) {
            System.err.println("Empty situation");
            return false;
        }
        return true;
    }

    void calculateAllSteps() {
        int index = 0;
        while (index < situations.size()) {
            Situation situation = situations.get(index);
            List<Situation> nextSituations = calculateNextSituations(situation);
            nextSituations.removeIf(sit -> sit.strips.isEmpty());
            situations.addAll(nextSituations);
            index++;
        }
        Situation finalSituation = new Situation(Collections.emptyList());
        finalSituation.isWin = -1;
        situations.add(new Situation(Collections.emptyList()));
    }


    int calculateWins(int index) {
        if (situations.size() == 1) {
            return 0;
        }
        Situation currentSituation = situations.get(index);
        if (currentSituation.isWin != 0) {
            return currentSituation.isWin;
        }
        List<Situation> nextSituations = calculateNextSituations(currentSituation);
        boolean foundFail = false;
        for (Situation situation : nextSituations) {
            for (int i = index + 1; i < situations.size(); i++) {
                Situation realSituation = situations.get(i);
                if (realSituation.equals(situation)) {
                    calculateWins(i);
                    if (realSituation.isWin == -1) {
                        foundFail = true;
                    }
                }
            }
        }
        currentSituation.isWin = foundFail ? 1 : -1;
        return currentSituation.isWin;
    }


    private List<Situation> calculateNextSituations(Situation situation) {
        Set<Situation> nextSituations = new HashSet<>();
        List<Integer> strips = situation.strips;
        for (int i = 0; i < strips.size(); i++) {
            int strip = strips.get(i);
            int left = 0;
            while (left + step <= strip) {
                List<Integer> newStrips = new ArrayList<>(strips);
                newStrips.remove(i);
                newStrips.add(left);
                newStrips.add(strip - left - step);
                left++;
                Situation newSituation = new Situation(newStrips);
                normalize(newSituation);
                nextSituations.add(newSituation);
            }
        }
        return new ArrayList<>(nextSituations);
    }
}
