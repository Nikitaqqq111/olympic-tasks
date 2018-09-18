package training.two.e;

class Summator {

    static int[] sum(int[] firstNumber, int[] secondNumber) {
        int[] result = new int[firstNumber.length + 1];
        int carry = 0;
        for (int index = 0; index < secondNumber.length; index++) {
            int sum = firstNumber[index] + secondNumber[index] + carry;
            result[index] = sum % 10;
            carry = sum / 10;
        }
        if (firstNumber.length == secondNumber.length) {
            result[firstNumber.length] = carry;
            return result;
        }
        for (int remainingIndex = secondNumber.length; remainingIndex < firstNumber.length; remainingIndex++) {
            int sum = firstNumber[remainingIndex] + carry;
            result[remainingIndex] = sum % 10;
            carry = sum / 10;
        }
        return result;
    }

}
