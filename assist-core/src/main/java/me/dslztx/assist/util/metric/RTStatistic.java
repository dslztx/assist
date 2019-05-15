package me.dslztx.assist.util.metric;

import java.util.concurrent.ConcurrentHashMap;

import me.dslztx.assist.util.ObjectAssist;

public class RTStatistic extends Statistic<ConcurrentHashMap<String, RTCounter>> {

    ConcurrentHashMap<String, RTCounter> stat = new ConcurrentHashMap<String, RTCounter>();

    public void incr(String key, long timeCost) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new RTCounter());

            RTCounter avgCounter = stat.get(key);

            if (ObjectAssist.isNotNull(avgCounter)) {
                avgCounter.incr(timeCost);
            }
        } finally {
            doReadUnLock();
        }
    }

    @Override
    protected ConcurrentHashMap<String, RTCounter> obtain() {
        return stat;
    }

    @Override
    protected void reset() {
        stat = new ConcurrentHashMap<String, RTCounter>();
    }
}
