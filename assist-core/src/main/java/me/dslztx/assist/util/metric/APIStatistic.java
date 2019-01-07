package me.dslztx.assist.util.metric;

import java.util.concurrent.ConcurrentHashMap;

import me.dslztx.assist.util.ObjectAssist;

public class APIStatistic extends Statistic<ConcurrentHashMap<String, APICounter>> {

    ConcurrentHashMap<String, APICounter> stat = new ConcurrentHashMap<String, APICounter>();

    public void incrSuccess(String key, long timeCost) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new APICounter());

            APICounter apiCounter = stat.get(key);

            if (ObjectAssist.isNotNull(apiCounter)) {
                apiCounter.incrSuccess(timeCost);
            }
        } finally {
            doReadUnLock();
        }
    }

    public void incrFail(String key, long timeCost) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new APICounter());

            APICounter apiCounter = stat.get(key);

            if (ObjectAssist.isNotNull(apiCounter)) {
                apiCounter.incrFail(timeCost);
            }
        } finally {
            doReadUnLock();
        }
    }

    public void addSuccess(String key, long num, long timeCost) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new APICounter());

            APICounter apiCounter = stat.get(key);

            if (ObjectAssist.isNotNull(apiCounter)) {
                apiCounter.addSuccess(num, timeCost);
            }
        } finally {
            doReadUnLock();
        }
    }

    public void addFail(String key, long num, long timeCost) {
        doReadLock();
        try {
            stat.putIfAbsent(key, new APICounter());

            APICounter apiCounter = stat.get(key);

            if (ObjectAssist.isNotNull(apiCounter)) {
                apiCounter.addFail(num, timeCost);
            }
        } finally {
            doReadUnLock();
        }
    }

    @Override
    protected ConcurrentHashMap<String, APICounter> obtain() {
        return stat;
    }

    @Override
    protected void reset() {
        stat = new ConcurrentHashMap<String, APICounter>();
    }
}
