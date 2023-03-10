package me.dslztx.assist.util;

import java.lang.reflect.Field;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 * @date 2018-10-01
 * @updated 2018-10-01
 */
public class ObjectAssist {

    private static final Logger logger = LoggerFactory.getLogger(ObjectAssist.class);

    @Deprecated
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    @Deprecated
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    @Deprecated
    public static boolean equals(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }

        if (a != null) {
            return a.equals(b);
        }

        return false;
    }

    /**
     * 有些对象的equals()方法已经完成自定义，这里强行自定义
     */
    public static boolean equalsGenerally(Object a, Object b) {
        if (a == null || b == null) {
            if (a == null && b == null) {
                return true;
            } else {
                return false;
            }
        }

        if (a.getClass() != b.getClass()) {
            return false;
        }

        Class clz = a.getClass();

        if (clz == Boolean.class || clz == Character.class || clz == Byte.class || clz == Short.class
            || clz == Integer.class || clz == Long.class || clz == Float.class || clz == Double.class) {
            return a.equals(b);
        } else if (clz == String.class || clz == Object.class || clz == Class.class) {
            return a.equals(b);
        } else if (clz.isArray()) {
            return arrayEquals(a, b);
        } else if (a instanceof Collection) {
            Collection aCollection = (Collection)a;
            Collection bCollection = (Collection)b;

            if (aCollection.size() != bCollection.size()) {
                return false;
            }

            Iterator aIterator = aCollection.iterator();
            Iterator bIterator = bCollection.iterator();

            while (aIterator.hasNext()) {
                if (!equalsGenerally(aIterator.next(), bIterator.next())) {
                    return false;
                }
            }
            return true;
        } else {
            return a.equals(b) || innerEquals(a, b);
        }
    }

    private static boolean arrayEquals(Object a, Object b) {
        Class componentClz = a.getClass().getComponentType();
        if (componentClz.isPrimitive()) {
            if (componentClz == boolean.class) {
                boolean[] aArray = (boolean[])a;
                boolean[] bArray = (boolean[])b;
                return Arrays.equals(aArray, bArray);

            } else if (componentClz == char.class) {
                char[] aArray = (char[])a;
                char[] bArray = (char[])b;
                return Arrays.equals(aArray, bArray);

            } else if (componentClz == byte.class) {
                byte[] aArray = (byte[])a;
                byte[] bArray = (byte[])b;
                return Arrays.equals(aArray, bArray);

            } else if (componentClz == short.class) {
                short[] aArray = (short[])a;
                short[] bArray = (short[])b;
                return Arrays.equals(aArray, bArray);

            } else if (componentClz == int.class) {
                int[] aArray = (int[])a;
                int[] bArray = (int[])b;
                return Arrays.equals(aArray, bArray);

            } else if (componentClz == long.class) {
                long[] aArray = (long[])a;
                long[] bArray = (long[])b;
                return Arrays.equals(aArray, bArray);

            } else if (componentClz == float.class) {
                float[] aArray = (float[])a;
                float[] bArray = (float[])b;
                return Arrays.equals(aArray, bArray);

            } else {
                // double.class
                double[] aArray = (double[])a;
                double[] bArray = (double[])b;
                return Arrays.equals(aArray, bArray);
            }
        } else {
            Object[] aArray = (Object[])a;
            Object[] bArray = (Object[])b;

            if (aArray.length != bArray.length) {
                return false;
            }

            for (int index = 0; index < aArray.length; index++) {
                if (!equalsGenerally(aArray[index], bArray[index])) {
                    return false;
                }
            }
            return true;
        }
    }

    private static boolean innerEquals(Object a, Object b) {
        Class clz = a.getClass();

        Set<Field> allFields = new HashSet<Field>(16);

        allFields.addAll(Arrays.asList(clz.getFields()));

        allFields.addAll(Arrays.asList(clz.getDeclaredFields()));

        for (Field field : allFields) {
            field.setAccessible(true);

            try {
                if (!equalsGenerally(field.get(a), field.get(b))) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                logger.error("", e);
                return false;
            }
        }

        return true;
    }
}
