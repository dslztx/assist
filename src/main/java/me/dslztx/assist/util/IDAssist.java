package me.dslztx.assist.util;

import java.util.concurrent.atomic.AtomicLong;

//todo
public class IDAssist {

    private static ThreadLocal<Long> timer = new ThreadLocal<Long>() {
        public Long initialValue() {
            return 0L;
        }
    };

    private static AtomicLong atomicLong = new AtomicLong(0);

    public static Long obtainThreadUniqueId() {
        Long id = timer.get();
        timer.set(id + 1);
        if (id == Long.MAX_VALUE) {
            timer.set(0L);
        } else {
            timer.set(id + 1);
        }
        return id;
    }

    public static Long obtainApplicationUniqueId() {
        Long result = atomicLong.get();

        if (!atomicLong.compareAndSet(Long.MAX_VALUE, 0)) {
            atomicLong.incrementAndGet();
        }

        return result;
    }
}
