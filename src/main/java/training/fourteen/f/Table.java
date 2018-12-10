package training.fourteen.f;

public class Table {

    private final String YES = "YES";
    private final String NO = "NO";
    private final String DURING = "DURING";

    private String[][] table;
    private String[][] founded;
    private double[][] result;

    Table(String[][] table) {
        this.table = table;
        founded = new String[table.length][table[0].length];
        result = new double[table.length][table[0].length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (table[i][j] == null) {
                    table[i][j] = "";
                }
                if ("".equals(table[i][j])) {
                    founded[i][j] = YES;
                    result[i][j] = 0.0d;
                } else {
                    founded[i][j] = NO;
                }
            }
        }
    }

    double calculate(int i, int j) {
        if (YES.equals(founded[i][j])) {
            return result[i][j];
        }
        if (DURING.equals(founded[i][j])) {
            return 1_000_000;
        }
        founded[i][j] = DURING;
        Wrapper pos = new Wrapper();
        double subResult = expression(table[i][j], pos);
        founded[i][j] = YES;
        result[i][j] = subResult;
        return subResult;
    }

    private double expression(String string, Wrapper pos) {
        double addendum = addendum(string, pos);
        while (pos.x < string.length() && (string.charAt(pos.x) == '+' || string.charAt(pos.x) == '-')) {
            pos.inc();
            if (string.charAt(pos.x - 1) == '+') {
                addendum += addendum(string, pos);
            } else {
                addendum -= addendum(string, pos);
            }
        }
        return addendum;
    }

    private double addendum(String string, Wrapper pos) {
        double multiplier = multiplier(string, pos);
        while (pos.x < string.length() && (string.charAt(pos.x) == '*' || string.charAt(pos.x) == '/')) {
            pos.inc();
            if (string.charAt(pos.x - 1) == '*') {
                multiplier *= multiplier(string, pos);
            } else {
                double divider = multiplier(string, pos);
                if (divider == 0.0d) {
                    return divider;
                }
                multiplier /= divider;
            }
        }
        return multiplier;
    }

    private double multiplier(String string, Wrapper pos) {
        if (string.charAt(pos.x) == '(') {
            StringBuilder sb = new StringBuilder();
            pos.inc();
            while (string.charAt(pos.x) != ')') {
                sb.append(string.charAt(pos.x));
                pos.inc();
            }
            pos.inc();
            return expression(sb.toString(), new Wrapper());
        }
        if (Character.isDigit(string.charAt(pos.x))) {
            double number = 0;
            while (pos.x < string.length() && Character.isDigit(string.charAt(pos.x))) {
                number = number * 10 + Integer.parseInt(string.substring(pos.x, pos.x + 1));
                pos.inc();
            }
            return number;
        }
        char letter = string.charAt(pos.x);
        pos.inc();
        int i = Integer.parseInt(string.substring(pos.x, pos.x + 1)) - 1;
        int j = letter - 'A';
        pos.inc();
        return calculate(i, j);
    }

}

class Wrapper {
    int x = 0;

    void inc() {
        x = x + 1;
    }
}
