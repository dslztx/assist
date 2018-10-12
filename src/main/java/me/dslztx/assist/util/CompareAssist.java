package me.dslztx.assist.util;

/**
 * @author dslztx
 */
public class CompareAssist {

    public static <T extends Comparable<T>> boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<T>> boolean lessEqual(T a, T b) {
        return a.compareTo(b) <= 0;
    }

    public static <T extends Comparable<T>> boolean greater(T a, T b) {
        return a.compareTo(b) > 0;
    }

    public static <T extends Comparable<T>> boolean greaterEqual(T a, T b) {
        return a.compareTo(b) >= 0;
    }

    public static <T extends Comparable<T>> boolean equal(T a, T b) {
        return a.compareTo(b) == 0;
    }

    public static String diff(String a, String b) {
        if (ObjectAssist.equals(a, b)) {
            return "no diff";
        }

        if (StringAssist.isEmpty(a) || StringAssist.isEmpty(b)) {
            if (StringAssist.isEmpty(a)) {
                return "" + "\nvs\n" + b;
            } else {
                return a + "\nvs\n" + "";
            }
        }

        int startA = 0;
        int startB = 0;
        while (startA < a.length() && startB < b.length() && a.charAt(startA) == b.charAt(startB)) {
            startA++;
            startB++;
        }

        if (startA == a.length() || startB == b.length()) {
            if (startA == a.length()) {
                return b.substring(startB);
            } else {
                return a.substring(startA);
            }
        }

        int endA = a.length() - 1;
        int endB = b.length() - 1;
        while (endA >= 0 && endB >= 0 && a.charAt(endA) == b.charAt(endB)) {
            endA--;
            endB--;
        }

        if (endA < 0 || endB < 0) {
            if (endA < 0) {
                return b.substring(0, endB + 1);
            } else {
                return a.substring(0, endA + 1);
            }
        }

        if (startA <= endA && startB <= endB) {
            return a.substring(startA, endA + 1) + "\n\n\n" + b.substring(startB, endB + 1);
        } else {
            if (endB > endA) {
                return b.substring(startB, endB + startA - endA);
            } else {
                return a.substring(startA, endA + startB - endB);
            }
        }
    }
}
