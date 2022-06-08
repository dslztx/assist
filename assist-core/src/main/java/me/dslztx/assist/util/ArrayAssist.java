package me.dslztx.assist.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dslztx
 */
public class ArrayAssist {

    public static int obtainSizeDefaultZero(Object[] array) {
        if (isEmpty(array)) {
            return 0;
        }

        return array.length;
    }

    public static int obtainSizeDefaultZero(byte[] array) {
        if (isEmpty(array)) {
            return 0;
        }

        return array.length;
    }

    public static int obtainSizeDefaultZero(int[] array) {
        if (isEmpty(array)) {
            return 0;
        }

        return array.length;
    }

    public static boolean isEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object[] array) {
        if (array != null && array.length != 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(byte[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(byte[] array) {
        if (array != null && array.length != 0)
            return true;
        return false;
    }

    public static boolean isEmpty(int[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(int[] array) {
        if (array != null && array.length != 0)
            return true;
        return false;
    }

    /**
     * 报错：List<Integer> list = Arrays.asList(new int[]{423, 523}); <br/>
     * 原因分析：将整个int[]作为一个Object<br/>
     * 不报错：List<Integer> list = Arrays.asList(new Integer[]{423, 523});
     */
    public static List<Integer> toList(int[] array) {
        List<Integer> result = new ArrayList<Integer>();
        if (isEmpty(array)) {
            return result;
        }

        for (int element : array) {
            result.add(element);
        }

        return result;
    }

    public static List<Byte> toList(byte[] array) {
        List<Byte> result = new ArrayList<Byte>();
        if (isEmpty(array)) {
            return result;
        }

        for (byte element : array) {
            result.add(element);
        }

        return result;
    }

    public static List<Integer> toList(Integer[] array) {
        List<Integer> result = new ArrayList<Integer>();
        if (isEmpty(array)) {
            return result;
        }

        result.addAll(Arrays.asList(array));

        return result;
    }
}
