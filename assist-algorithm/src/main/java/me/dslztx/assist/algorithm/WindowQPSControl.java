package me.dslztx.assist.algorithm;

import java.util.concurrent.TimeUnit;

/**
 * “滑动窗口”QPS控制算法
 */
public class WindowQPSControl {

    private final Object lock = new Object();

    /**
     * 循环队列，元素为“请求时间，以毫秒为单位”
     */
    private CircleQueue<Long> queue;

    /**
     * QPS控制时间区间，单位为毫秒
     */
    private long period;

    /**
     * @param capacity QPS控制请求限制次数，比如“100”
     * @param period QPS控制时间区间，比如“1”
     * @param timeUnit QPS控制时间区间单位，比如单位为“秒”
     */
    public WindowQPSControl(int capacity, int period, TimeUnit timeUnit) {
        if (capacity < 1) {
            throw new IllegalArgumentException("illegal capacity: " + capacity);
        }

        if (timeUnit == null) {
            throw new IllegalArgumentException("timeunit not specified");
        }

        this.queue = new CircleQueue<Long>(capacity);

        this.period = timeUnit.toMillis(period);
    }

    public boolean isPass() {
        synchronized (lock) {
            long curTime = System.currentTimeMillis();
            if (queue.isNotFull()) {
                queue.push(curTime);
                return true;
            }

            if (curTime >= period + queue.top()) {
                queue.pop();
                queue.push(curTime);
                return true;
            }

            return false;
        }
    }
}
