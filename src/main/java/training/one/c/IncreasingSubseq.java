package training.one.c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class IncreasingSubseq {

    static List<Integer> findMaxSubseq(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("numbers list is empty");
        }
        int[] maxs = new int[numbers.size()];
        int[] prevs = new int[numbers.size()];
        maxs[0] = 1;
        int maxCount = 1;
        int maxIndex = 0;
        prevs[0] = -1;
        for (int i = 1; i < numbers.size(); i++) {
            int localMax = 1;
            int localPrevs = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (numbers.get(i) >= numbers.get(j)) {
                    if (maxs[j] + 1 > localMax) {
                        localMax = maxs[j] + 1;
                        localPrevs = j;
                    }
                }
            }
            if (localMax > maxCount) {
                maxIndex = i;
                maxCount = localMax;
            }
            prevs[i] = localPrevs;
            maxs[i] = localMax;
        }
        List<Integer> maxSeqSubList = new ArrayList<>();
        while(maxIndex != -1) {
            maxSeqSubList.add(numbers.get(maxIndex));
            maxIndex = prevs[maxIndex];
        }
        Collections.reverse(maxSeqSubList);
        return maxSeqSubList;
    }

}
