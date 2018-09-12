package training.one.b;

import java.util.List;

class NumberUtils {

    static String calculateSign(List<Integer> numbers, int goal) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("numbers are empty");
        }
        if (numbers.size() == 1 && numbers.get(0) == goal) {
            return numbers.get(0).toString();
        }
        if (numbers.size() == 1 && numbers.get(0) != goal) {
            return "No solution";
        }
        char[] signs = new char[numbers.size() - 1];
        boolean solutionIsFind = calculate(signs, 1, numbers, goal - numbers.get(0));
        if (!solutionIsFind) {
            return "No solution";
        }
        StringBuilder sb = new StringBuilder().append(numbers.get(0));
        for (int i = 1; i < numbers.size(); i++) {
            sb.append(signs[i - 1]).append(numbers.get(i));
        }
        return sb.toString();
    }

    private static boolean calculate(char[] signs, int index, List<Integer> numbers, int goal) {
        if (index == numbers.size() - 1) {
            if (numbers.get(index) - goal == 0) {
                signs[index - 1] = '+';
                return true;
            } else if (numbers.get(index) + goal == 0) {
                signs[index - 1] = '-';
                return true;
            } else {
                return false;
            }
        }
        if (calculate(signs, index + 1, numbers, goal - numbers.get(index))) {
            signs[index - 1] = '+';
            return true;
        }
        if (calculate(signs, index + 1, numbers, goal + numbers.get(index))) {
            signs[index - 1] = '-';
            return true;
        }
        return false;
    }

}
