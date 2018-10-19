package me.dslztx.assist.util;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class QuartzScheduleAssistTest {

  private static final Logger logger = LoggerFactory.getLogger(QuartzScheduleAssistTest.class);

  @Test
  public void submitCronJob() {
    try {
      QuartzScheduleAssist.submitCronJob(new Runnable() {
        @Override
        public void run() {
          System.out.println("hello world");
        }
      }, "0 */1 * * * ?");

      Thread.sleep(10 * 60 * 1000L);
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }
}
