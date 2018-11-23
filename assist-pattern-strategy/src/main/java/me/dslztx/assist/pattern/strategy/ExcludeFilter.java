package me.dslztx.assist.pattern.strategy;

public interface ExcludeFilter<T> {

    boolean exclude(T t);
}
