package me.dslztx.assist.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import me.dslztx.assist.bean.Counter;

/**
 * @author dslztx
 */
public class CounterStatAssist {

    private static ConcurrentHashMap<String, Counter> stats = new ConcurrentHashMap<String, Counter>();

    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void incr(String name) {
        lock.readLock().lock();
        try {
            stats.putIfAbsent(name, new Counter());

            Counter stat = stats.get(name);
            if (stat != null) {
                stat.incr();
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void incrSuccess(String name) {
        lock.readLock().lock();
        try {
            stats.putIfAbsent(name, new Counter());

            Counter stat = stats.get(name);
            if (stat != null) {
                stat.incrSuccess();
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void incrFail(String name) {
        lock.readLock().lock();
        try {
            stats.putIfAbsent(name, new Counter());

            Counter stat = stats.get(name);
            if (stat != null) {
                stat.incrFail();
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void addTimeCost(String name, long timeCost) {
        lock.readLock().lock();
        try {
            stats.putIfAbsent(name, new Counter());

            Counter stat = stats.get(name);
            if (stat != null) {
                stat.addTimeCost(timeCost);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void incrWithTimeCost(String name, long timeCost) {
        lock.readLock().lock();
        try {
            stats.putIfAbsent(name, new Counter());

            Counter stat = stats.get(name);
            if (stat != null) {
                stat.incrWithTimeCost(timeCost);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void incrSuccessWithTimeCost(String name, long timeCost) {
        lock.readLock().lock();
        try {
            stats.putIfAbsent(name, new Counter());

            Counter stat = stats.get(name);
            if (stat != null) {
                stat.incrSuccessWithTimeCost(timeCost);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void incrFailWithTimeCost(String name, long timeCost) {
        lock.readLock().lock();
        try {
            stats.putIfAbsent(name, new Counter());

            Counter stat = stats.get(name);
            if (stat != null) {
                stat.incrFailWithTimeCost(timeCost);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public static Counter obtainAndClear(String name) {
        lock.writeLock().lock();

        try {
            Counter stat = stats.get(name);
            stats.remove(name);
            return stat;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
