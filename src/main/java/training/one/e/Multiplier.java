package training.one.e;

import java.util.ArrayList;
import java.util.List;

class Multiplier {

    static Integer[] multiply(Integer[] firstNumber, Integer[] secondNumber) {
        Integer[] result = new Integer[firstNumber.length + secondNumber.length];
        for (int d = 0; d < result.length; d++) {
            result[d] = 0;
        }
        if (firstNumber.length < secondNumber.length) {
            Integer[] swap = firstNumber;
            firstNumber = secondNumber;
            secondNumber = swap;
        }
        for (int i = 0; i < secondNumber.length; i++) {
            for (int j = 0; j < firstNumber.length; j++) {
                int digitMulti = secondNumber[i] * firstNumber[j];
                int k = 0;
                int sumDigit;
                do {
                    sumDigit = digitMulti + result[i + j + k];
                    result[i + j + k] = sumDigit % 10;
                    digitMulti = sumDigit / 10;
                    k++;
                } while (digitMulti > 0);
            }
        }
        return result;
    }

    static Integer[] convertToArray(int number) {
        List<Integer> digits = new ArrayList<>();
        while (number != 0) {
            digits.add(number % 10);
            number = number / 10;
        }
        Integer[] numberIntoArray = new Integer[digits.size()];
        digits.toArray(numberIntoArray);
        return numberIntoArray;
    }

}
