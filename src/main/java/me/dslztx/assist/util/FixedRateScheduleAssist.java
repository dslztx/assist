package me.dslztx.assist.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author dslztx
 */
public class FixedRateScheduleAssist {

  private static ScheduledExecutorService threadPool = Executors
      .newScheduledThreadPool(10, new ThreadFactory() {

        public Thread newThread(Runnable r) {
          Thread t = new Thread(r, "FixedRateScheduleThread");
          t.setDaemon(true);
          return t;
        }
      });

  public static void submitFixedRateJob(Runnable command, long initialDelay, long period,
      TimeUnit unit) {
    threadPool.scheduleAtFixedRate(command, initialDelay, period, unit);
  }

}
