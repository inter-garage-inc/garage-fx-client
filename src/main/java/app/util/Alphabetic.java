package app.util;

public class Alphabetic {
    public static String parseInt(Integer i) {
        return i < 0 ? "" : parseInt((i / 26) - 1) + (char) (65 + i % 26);
    }

    public static Integer parseString(String s) {
        int n = 0;
        for(var p = 0; p < s.length(); p++) {
            n = (s.codePointAt(p) - 64) + (n * 26);
        }
        return n;
    }
}