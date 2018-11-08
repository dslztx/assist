package me.dslztx.assist.util;

public class PrimitiveAssist {

    public static boolean less(double a, double b) {
        // 自动装箱
        return CompareAssist.less(a, b);
    }

    public static boolean lessEqual(double a, double b) {
        // 自动装箱
        return CompareAssist.lessEqual(a, b);
    }

    public static boolean greater(double a, double b) {
        // 自动装箱
        return CompareAssist.greater(a, b);
    }

    public static boolean greaterEqual(double a, double b) {
        // 自动装箱
        return CompareAssist.greaterEqual(a, b);
    }

    public static boolean equal(double a, double b) {
        // 自动装箱
        return CompareAssist.equal(a, b);
    }

    public static boolean less(float a, float b) {
        // 自动装箱
        return CompareAssist.less(a, b);
    }

    public static boolean lessEqual(float a, float b) {
        // 自动装箱
        return CompareAssist.lessEqual(a, b);
    }

    public static boolean greater(float a, float b) {
        // 自动装箱
        return CompareAssist.greater(a, b);
    }

    public static boolean greaterEqual(float a, float b) {
        // 自动装箱
        return CompareAssist.greaterEqual(a, b);
    }

    public static boolean equal(float a, float b) {
        // 自动装箱
        return CompareAssist.equal(a, b);
    }
}
