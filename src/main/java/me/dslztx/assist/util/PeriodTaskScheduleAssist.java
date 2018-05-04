package me.dslztx.assist.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class PeriodTaskScheduleAssist {

  private static ScheduledExecutorService threadPool = Executors
      .newScheduledThreadPool(3, new ThreadFactory() {

        public Thread newThread(Runnable r) {
          Thread t = new Thread(r, "PeriodTaskScheduleThread");
          t.setDaemon(true);
          return t;
        }
      });

  public static void schedule(Runnable command, long initialDelay, long period, TimeUnit unit) {
    threadPool.scheduleAtFixedRate(command, initialDelay, period, unit);
  }

}
