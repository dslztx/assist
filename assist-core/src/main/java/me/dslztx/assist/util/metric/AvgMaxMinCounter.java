package me.dslztx.assist.util.metric;

import java.util.concurrent.atomic.AtomicLong;

public class AvgMaxMinCounter {

    final Object lock = new Object();

    AtomicLong cnt = new AtomicLong(0);

    AtomicLong total = new AtomicLong(0);

    long maxValue = Long.MIN_VALUE;

    long minValue = Long.MAX_VALUE;

    public AtomicLong getCnt() {
        return cnt;
    }

    public AtomicLong getTotal() {
        return total;
    }

    /**
     * 这里不需要考虑打印时，只执行了部分统计语句，因为在打印时，需要获取“写”锁，方法执行时持有“读”锁，故必然会等待所有语句执行完成的，最终能够获得“最终统计准确性”
     */
    public void incr(long value) {
        this.cnt.incrementAndGet();
        this.total.addAndGet(value);

        synchronized (lock) {
            if (value > maxValue) {
                maxValue = value;
            }

            if (value < minValue) {
                minValue = value;
            }
        }
    }

    public long avgValue() {
        if (cnt.get() == 0L) {
            return 0L;
        } else {
            return total.get() / cnt.get();
        }
    }

    public long maxValue() {
        return maxValue;
    }

    public long minValue() {
        return minValue;
    }
}
