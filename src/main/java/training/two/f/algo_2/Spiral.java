package training.two.f.algo_2;

class Spiral {

    private final int n;
    private final int[][] table;


    Spiral(int n) {
        this.n = n;
        this.table = new int[n + 2][n + 2];
    }

    int[][] construct() {
        fillBorder();
        int i = 1, j = 1, count = 0;
        while (table[i][j] == 0) {
            table[i][j] = ++count;
            if (table[i + Direction.di][j + Direction.dj] != 0) {
                Direction.changeDirection();
            }
            i += Direction.di;
            j += Direction.dj;
        }
        return table;
    }

    private void fillBorder() {
        for (int i = 0; i < table.length; i++) {
            table[0][i] = 1;
        }
        for (int i = 0; i < table.length; i++) {
            table[i][0] = 1;
        }
        for (int i = 0; i < table.length; i++) {
            table[i][n + 1] = 1;
        }
        for (int i = 0; i < table.length; i++) {
            table[n + 1][i] = 1;
        }
    }

    private static class Direction {
        static int di = 0;
        static int dj = 1;

        static void changeDirection() {
            if (di == 0 && dj == 1) {
                di = 1;
                dj = 0;
                return;
            }
            if (di == 1 && dj == 0) {
                di = 0;
                dj = -1;
                return;
            }
            if (di == 0 && dj == -1) {
                di = -1;
                dj = 0;
                return;
            }
            if (di == -1 && dj == 0) {
                di = 0;
                dj = 1;
            }
        }
    }
}
