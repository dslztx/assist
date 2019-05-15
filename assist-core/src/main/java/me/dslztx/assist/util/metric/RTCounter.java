package me.dslztx.assist.util.metric;

import java.util.concurrent.atomic.AtomicLong;

public class RTCounter {

    final Object lock = new Object();

    AtomicLong cnt = new AtomicLong(0);

    AtomicLong timeCost = new AtomicLong(0);

    long maxRT = Long.MIN_VALUE;

    long minRT = Long.MAX_VALUE;

    public AtomicLong getCnt() {
        return cnt;
    }

    public AtomicLong getTimeCost() {
        return timeCost;
    }

    /**
     * 这里不需要考虑打印时，只执行了部分统计语句，因为在打印时，需要获取“写”锁，方法执行时持有“读”锁，故必然会等待所有语句执行完成的，最终能够获得“最终统计准确性”
     */
    public void incr(long timeCost) {
        this.cnt.incrementAndGet();
        this.timeCost.addAndGet(timeCost);

        synchronized (lock) {
            if (timeCost > maxRT) {
                maxRT = timeCost;
            }

            if (timeCost < minRT) {
                minRT = timeCost;
            }
        }
    }

    public long avgRT() {
        if (cnt.get() == 0L) {
            return 0L;
        } else {
            return timeCost.get() / cnt.get();
        }
    }

    public long maxRT() {
        return maxRT;
    }

    public long minRT() {
        return minRT;
    }
}
