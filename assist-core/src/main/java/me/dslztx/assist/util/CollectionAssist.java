package me.dslztx.assist.util;

import java.util.Collection;

import me.dslztx.assist.pattern.strategy.ExcludeFilter;
import me.dslztx.assist.pattern.strategy.IncludeFilter;

/**
 * @author dslztx
 */
public class CollectionAssist {

    public static boolean isEmpty(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection collection) {
        if (collection != null && !collection.isEmpty()) {
            return true;
        }
        return false;
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
}
