package me.dslztx.assist.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dslztx
 */
public class FixedRateScheduleAssist {

    private static final Logger logger = LoggerFactory.getLogger(FixedRateScheduleAssist.class);

    private static ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10, new ThreadFactory() {

        private AtomicLong threadID = new AtomicLong(0);

        public Thread newThread(Runnable r) {
            logger.info("create fixed rate schedule worker thread by {}", Thread.currentThread().getName());

            Thread t = new Thread(r, "FixedRateScheduleThread" + threadID.getAndIncrement());
            return t;
        }
    });

    public static ScheduledFuture submitFixedRateJob(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return threadPool.scheduleAtFixedRate(command, initialDelay, period, unit);
    }
}
