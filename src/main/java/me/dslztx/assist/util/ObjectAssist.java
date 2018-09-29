package me.dslztx.assist.util;

/**
 * @author dslztx
 */
public class ObjectAssist {

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean equals(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }

        if (a != null) {
            return a.equals(b);
        }

        return false;
    }
}
