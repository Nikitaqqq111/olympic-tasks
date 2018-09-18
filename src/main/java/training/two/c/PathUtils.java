package training.two.c;

class PathUtils {

    private static final char LEFT = 'L';
    private static final char UP = 'U';

    static char[][] findMinPath(int[][] table) {
        int[][] optimalTable = new int[table.length][table.length];
        char[][] optimalPaths = new char[table.length][table.length];
        for (int i = 1; i < optimalTable.length; i++) {
            for (int j = 1; j < optimalTable.length; j++) {
                int firstPath = table[i - 1][j] + table[i][j];
                int secondPath = table[i][j - 1] + table[i][j];
                if (firstPath < secondPath) {
                    optimalTable[i][j] = firstPath;
                    optimalPaths[i][j] = UP;
                } else {
                    optimalTable[i][j] = secondPath;
                    optimalPaths[i][j] = LEFT;
                }
            }
        }
        return optimalPaths;
    }
}
