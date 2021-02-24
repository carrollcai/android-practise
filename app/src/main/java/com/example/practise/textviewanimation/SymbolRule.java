package com.example.practise.textviewanimation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 考虑负数情况，即第一位为 "-"
 */
public class SymbolRule {
    private final static String SPECIAL_SYMBOL = "-+×÷";

    public static int indexOf(String str) {
        char[] symbols = SPECIAL_SYMBOL.toCharArray();
        for (char symbol : symbols) {
            int index = str.indexOf(String.valueOf(symbol));
            if (index > -1) {
                return index;
            }
        }
        return -1;
    }

    public static int findSecondSymbol(String str) {
        int i = 0;
        boolean isFirst = false;
        char[] charStr = str.toCharArray();

        for (char s : charStr) {
            if (SPECIAL_SYMBOL.contains(String.valueOf(s))) {
                if (!isFirst) {
                    isFirst = true;
                } else {
                    return i;
                }
            }
            i++;
        }

        return -1;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[0-9]+$");//这个也行
//        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;

    }
}
