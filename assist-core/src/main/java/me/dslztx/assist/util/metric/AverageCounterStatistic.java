package me.dslztx.assist.util.metric;

import java.util.concurrent.ConcurrentHashMap;

import me.dslztx.assist.util.ObjectAssist;

public class AverageCounterStatistic extends Statistic<ConcurrentHashMap<String, AverageCounter>> {

    ConcurrentHashMap<String, AverageCounter> stat = new ConcurrentHashMap<String, AverageCounter>();

    public void incr(String key, long timeCost) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new AverageCounter());

            AverageCounter avgCounter = stat.get(key);

            if (ObjectAssist.isNotNull(avgCounter)) {
                avgCounter.incr(timeCost);
            }
        } finally {
            doReadUnLock();
        }
    }

    public void add(String key, long num, long timeCost) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new AverageCounter());

            AverageCounter avgCounter = stat.get(key);

            if (ObjectAssist.isNotNull(avgCounter)) {
                avgCounter.add(num, timeCost);
            }
        } finally {
            doReadUnLock();
        }
    }

    @Override
    protected ConcurrentHashMap<String, AverageCounter> obtain() {
        return stat;
    }

    @Override
    protected void reset() {
        stat = new ConcurrentHashMap<String, AverageCounter>();
    }
}
