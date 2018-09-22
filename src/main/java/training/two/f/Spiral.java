package training.two.f;

class Spiral {

    private final int n;
    private final int[][] table;
    private int count = 0;
    Spiral(int n) {
        this.n = n;
        table = new int[n][n];
    }

    int[][] construct() {
        for (int i = 0; i < n / 2; i++) {
            table[i][i] = ++count;
            fillRight(i, i + 1, n - i - 1);
            fillDown(n - i - 1, i + 1, n - i - 1);
            fillLeft(n - i - 1, n - i - 2, i);
            fillUp(i, n - i - 2, i + 1);

        }
        if (n % 2 == 1) {
            table[n / 2][n / 2] = ++count;
        }
        return table;
    }

    private void fillRight(int rowNum, int from, int to) {
        for (int i = from; i <= to; i++) {
            table[rowNum][i] = ++count;
        }
    }

    private void fillDown(int columNum, int from, int to) {
        for (int i = from; i <= to; i++) {
            table[i][columNum] = ++count;
        }
    }

    private void fillLeft(int rowNum, int from, int to) {
        for (int i = from; i >= to; i--) {
            table[rowNum][i] = ++count;
        }
    }

    private void fillUp(int columnNum, int from, int to) {
        for (int i = from; i >= to; i--) {
            table[i][columnNum] = ++count;
        }
    }
}
