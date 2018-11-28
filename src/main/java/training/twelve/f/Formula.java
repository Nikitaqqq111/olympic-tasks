package training.twelve.f;

import java.util.HashMap;
import java.util.Map;

class Formula {

    private final String string;
    private int pos;
    private char ch;

    Formula(String string) {
        this.string = string;
        this.pos = 0;
        this.ch = string.charAt(pos);
    }

    private int number() {
        int n = 1;
        if (Character.isDigit(ch)) {
            n = ch - '0';
            nextChar();
            while (Character.isDigit(ch)) {
                n = n * 10 + (ch - '0');
                nextChar();
            }
        }
        return n;
    }

    private void nextChar() {
        if (pos < string.length() - 1) {
            pos++;
            ch = string.charAt(pos);
        } else {
            ch = '#';
        }
    }

    private Map<String, Integer> chem() {
        if (!(Character.isLetter(ch) && Character.toUpperCase(ch) == ch)) {
            throw new IllegalArgumentException();
        }
        String el = new String(new char[]{ch});
        nextChar();
        if (Character.isLetter(ch) && Character.toLowerCase(ch) == ch) {
            el += ch;
            nextChar();
        }
        Map<String, Integer> table = new HashMap<>();
        table.put(el, 1);
        return table;
    }

    private Map<String, Integer> sequence() {
        Map<String, Integer> table1 = element();
        int multiplier = number();
        mul(multiplier, table1);
        while ((Character.isLetter(ch) && Character.toUpperCase(ch) == ch) || ch == '(') {
            Map<String, Integer> table2 = element();
            mul(number(), table2);
            add(table1, table2);
        }
        return table1;
    }

    private Map<String, Integer> element() {
        if (Character.isLetter(ch)) {
            return chem();
        } else if (ch == '(') {
            nextChar();
            Map<String, Integer> result = sequence();
            nextChar();
            return result;
        }
        throw new IllegalArgumentException();
    }

    Map<String, Integer> formula() {
        int multiplier = number();
        Map<String, Integer> table1 = sequence();
        mul(multiplier, table1);
        while (ch == '+') {
            nextChar();
            int multiplier2 = number();
            Map<String, Integer> table2 = sequence();
            mul(multiplier2, table2);
            add(table1, table2);
        }
        return table1;
    }

    private void mul(int multiplier, Map<String, Integer> table) {
        for (String key : table.keySet()) {
            table.put(key, table.get(key) * multiplier);
        }
    }

    private void add(Map<String, Integer> table1, Map<String, Integer> table2) {
        for (String key : table2.keySet()) {
            if (table1.containsKey(key)) {
                table1.put(key, table1.get(key) + table2.get(key));
            } else {
                table1.put(key, table2.get(key));
            }
        }
    }

}
