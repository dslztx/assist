package me.dslztx.assist.util.metric;

import java.util.concurrent.ConcurrentHashMap;

public class AvgMaxMinStatistic extends Statistic<ConcurrentHashMap<String, AvgMaxMinCounter>> {

    ConcurrentHashMap<String, AvgMaxMinCounter> stat = new ConcurrentHashMap<String, AvgMaxMinCounter>();

    public void incr(String key, long value) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new AvgMaxMinCounter());

            AvgMaxMinCounter avgCounter = stat.get(key);

            avgCounter.incr(value);
        } finally {
            doReadUnLock();
        }
    }

    @Override
    protected ConcurrentHashMap<String, AvgMaxMinCounter> obtain() {
        return stat;
    }

    @Override
    protected void reset() {
        stat = new ConcurrentHashMap<String, AvgMaxMinCounter>();
    }
}
