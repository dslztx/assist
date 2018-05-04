package me.dslztx.assist.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class PeriodTaskScheduleAssistTest {

  private static final Logger logger = LoggerFactory.getLogger(PeriodTaskScheduleAssistTest.class);

  private static long id = 0;

  @Test
  public void schedule() throws Exception {
    try {
      System.out.println(new Date());

      for (int index = 0; index < 10; index++) {
        PeriodTaskScheduleAssist.schedule(new Runnable() {
          private long uniqueID = id++;

          @Override
          public void run() {
            System.out.println("uniqueID: " + uniqueID + ", date: " + new Date());
          }
        }, 20, 10, TimeUnit.SECONDS);
      }

      Thread.sleep(40000);
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

}