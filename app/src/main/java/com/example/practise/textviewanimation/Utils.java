package com.example.practise.textviewanimation;

public class Utils {

    public static String removeCharAt(String s, int pos) {
        if (pos < 0) return s;
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public static String appendCharAt(String s, String newS, int pos) {
        if (pos < 0) return s;
        return s.substring(0, pos) + newS + s.substring(pos);
    }
}
