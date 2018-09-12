package training.one.a;

class NumberUtils {

    static boolean isSimple(int number) {
        int sqrt = (int) Math.sqrt(number);
        for (int divider = 2; divider <= sqrt; divider++) {
            if (number % divider == 0) {
                return false;
            }
        }
        return true;
    }
}
