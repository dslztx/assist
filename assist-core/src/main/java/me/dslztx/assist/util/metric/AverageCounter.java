package me.dslztx.assist.util.metric;

import java.util.concurrent.atomic.AtomicLong;

public class AverageCounter {

    AtomicLong cnt = new AtomicLong(0);

    AtomicLong timeCost = new AtomicLong(0);

    /**
     * 这里不需要考虑打印时，只执行了部分统计语句，因为在打印时，需要获取“写”锁，方法执行时持有“读”锁，故必然会等待所有语句执行完成的，最终能够获得“最终统计准确性”
     */
    public void incr(long timeCost) {
        this.cnt.incrementAndGet();
        this.timeCost.addAndGet(timeCost);
    }

    public void add(long num, long timeCost) {
        this.cnt.addAndGet(num);
        this.timeCost.addAndGet(timeCost);
    }

    public long avg() {
        if (cnt.get() == 0L) {
            return 0L;
        } else {
            return timeCost.get() / cnt.get();
        }
    }
}
