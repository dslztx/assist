package me.dslztx.assist.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import me.dslztx.assist.pattern.strategy.ExcludeFilter;
import me.dslztx.assist.pattern.strategy.IncludeFilter;

/**
 * @author dslztx
 */
public class CollectionAssist {

    public static int obtainSizeDefaultZero(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return 0;
        }

        return collection.size();
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    public static int[] convertToIntArray(Collection<Integer> collection) {
        if (CollectionAssist.isEmpty(collection)) {
            return new int[0];
        }

        int[] intArray = new int[collection.size()];
        int size = 0;
        for (Integer e : collection) {
            if (ObjectAssist.isNull(e)) {
                continue;
            }

            intArray[size++] = e;
        }

        if (size == intArray.length) {
            return intArray;
        } else {
            return Arrays.copyOfRange(intArray, 0, size);
        }
    }

    public static <T> void filterExclude(Collection<T> source, ExcludeFilter<T> filter, Collection<T> to) {
        if (ObjectAssist.isNull(to)) {
            throw new RuntimeException("the destination collection can not be null");
        }

        if (CollectionAssist.isEmpty(source)) {
            return;
        }

        for (T element : source) {
            if (filter.exclude(element)) {
                continue;
            }

            to.add(element);
        }
    }

    public static <T> void filterInclude(Collection<T> source, IncludeFilter<T> filter, Collection<T> to) {
        if (ObjectAssist.isNull(to)) {
            throw new RuntimeException("the destination collection can not be null");
        }

        if (CollectionAssist.isEmpty(source)) {
            return;
        }

        for (T element : source) {
            if (filter.include(element)) {
                to.add(element);
            }
        }
    }

    public static <T> T[] toArray(Collection<T> source, Class<T> elementClz) {
        if (ObjectAssist.isNull(source)) {
            return null;
        }

        // noinspection unchecked
        return source.toArray((T[])Array.newInstance(elementClz, source.size()));
    }
}
