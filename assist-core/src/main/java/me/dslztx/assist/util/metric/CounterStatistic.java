package me.dslztx.assist.util.metric;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import me.dslztx.assist.util.ObjectAssist;

public class CounterStatistic extends Statistic<ConcurrentHashMap<String, AtomicLong>> {

    ConcurrentHashMap<String, AtomicLong> stat = new ConcurrentHashMap<String, AtomicLong>();

    public void incr(String key) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new AtomicLong(0));

            AtomicLong value = stat.get(key);
            if (ObjectAssist.isNotNull(value)) {
                value.incrementAndGet();
            }
        } finally {
            doReadUnLock();
        }
    }

    public void add(String key, long num) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new AtomicLong(0));

            AtomicLong value = stat.get(key);
            if (ObjectAssist.isNotNull(value)) {
                value.addAndGet(num);
            }
        } finally {
            doReadUnLock();
        }
    }

    @Override
    protected ConcurrentHashMap<String, AtomicLong> obtain() {
        return stat;
    }

    @Override
    protected void reset() {
        stat = new ConcurrentHashMap<String, AtomicLong>();
    }
}
