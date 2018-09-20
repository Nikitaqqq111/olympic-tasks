package training.two.c;


class PathUtils {

    private static final int LEFT = 0;
    private static final int UP = 1;
    private static final int INITIAL = 2;
    private static final int PATH_TAG = 7;


    static int[][] findMinPath(int[][] table) {
        int n = table.length;
        int[] optimalPaths = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    optimalPaths[j] = table[i][j];
                    table[0][0] = INITIAL;
                    continue;
                }
                if (j == 0) {
                    optimalPaths[j] = optimalPaths[j] + table[i][j];
                    table[i][j] = UP;
                    continue;
                }
                if (i == 0) {
                    optimalPaths[j] = optimalPaths[j - 1] + table[i][j];
                    table[i][j] = LEFT;
                    continue;
                }
                int firstPathLength = optimalPaths[j] + table[i][j];
                int secondPathLength = optimalPaths[j - 1] + table[i][j];
                if (firstPathLength < secondPathLength) {
                    optimalPaths[j] = firstPathLength;
                    table[i][j] = UP;
                } else {
                    table[i][j] = LEFT;
                    optimalPaths[j] = secondPathLength;

                }
            }
        }
        int i = n - 1, j = n - 1;
        int direction = table[i][j];
        while (direction != INITIAL) {
            if (direction == UP) {
                table[i][j] = PATH_TAG;
                i--;
            }
            if (direction == LEFT) {
                table[i][j] = PATH_TAG;
                j--;
            }
            direction = table[i][j];
        }
        table[i][j] = PATH_TAG;
        for (int p = 0; p < n; p++) {
            for (int q = 0; q < n; q++) {
                if (table[p][q] != PATH_TAG) {
                    table[p][q] = 0;
                }
            }
        }
        return table;

    }
}
