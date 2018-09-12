package training.one.e;

class Pow {

    static Integer[] pow(Integer[] a, Integer n) {
        if (n == 1) {
            return a;
        }
        if (n % 2 == 0) {
            Integer[] b = pow(a, n / 2);
            return Multiplier.multiply(b, b);
        } else {
            return Multiplier.multiply(a, pow(a, n - 1));
        }
    }

}
