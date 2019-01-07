package me.dslztx.assist.util.metric;

import java.util.concurrent.atomic.AtomicLong;

public class APICounter {

    AtomicLong successCnt = new AtomicLong(0);

    AtomicLong failCnt = new AtomicLong(0);

    AtomicLong totalCnt = new AtomicLong(0);

    AtomicLong successTimeCost = new AtomicLong(0);

    AtomicLong failTimeCost = new AtomicLong(0);

    AtomicLong totalTimeCost = new AtomicLong(0);

    public AtomicLong getSuccessCnt() {
        return successCnt;
    }

    public AtomicLong getFailCnt() {
        return failCnt;
    }

    public AtomicLong getTotalCnt() {
        return totalCnt;
    }

    public AtomicLong getSuccessTimeCost() {
        return successTimeCost;
    }

    public AtomicLong getFailTimeCost() {
        return failTimeCost;
    }

    public AtomicLong getTotalTimeCost() {
        return totalTimeCost;
    }

    /**
     * 这里不需要考虑打印时，只执行了部分统计语句，因为在打印时，需要获取“写”锁，方法执行时持有“读”锁，故必然会等待所有语句执行完成的，最终能够获得“最终统计准确性”
     */
    public void incrSuccess(long timeCost) {
        successCnt.incrementAndGet();
        successTimeCost.addAndGet(timeCost);

        totalCnt.incrementAndGet();
        totalTimeCost.addAndGet(timeCost);
    }

    public void incrFail(long timeCost) {
        failCnt.incrementAndGet();
        failTimeCost.addAndGet(timeCost);

        totalCnt.incrementAndGet();
        totalTimeCost.addAndGet(timeCost);
    }

    public void addSuccess(long num, long timeCost) {
        successCnt.addAndGet(num);
        successTimeCost.addAndGet(timeCost);

        totalCnt.addAndGet(num);
        totalTimeCost.addAndGet(timeCost);
    }

    public void addFail(long num, long timeCost) {
        failCnt.addAndGet(num);
        failTimeCost.addAndGet(timeCost);

        totalCnt.addAndGet(num);
        totalTimeCost.addAndGet(timeCost);
    }
}
