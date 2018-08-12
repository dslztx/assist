package me.dslztx.assist.util;

import me.dslztx.assist.bean.SimpleProxyJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class QuartzScheduleAssist {

  private static final Logger logger = LoggerFactory.getLogger(QuartzScheduleAssist.class);

  private static Scheduler scheduler = null;

  private static volatile boolean init = false;

  /**
   * newJob()方法基于Class对象而不是实例对象是为了便于序列化JobDetail实例对象（Class类继承java.io.Serializable
   * 接口，实例对象对应的类不一定继承java.io.Serializable接口）<br/>
   *
   * Quartz设计考虑了持久化定时任务，因此需要序列化<br/>
   *
   * 但是，这样带来两个问题：1）该Job子类需要继承java.io.Serializable接口；2）该Job子类需要对Quartz包可见且存在
   * 一个无参构造方法，这样Quartz包内部才能创建一个该类的实例对象
   *
   * 因此，如果不考虑持久化（也就不用考虑序列化），可通过以下这种方式解决（通过JobDataMap传入任务实例对象），这才是常见应用场景
   */
  public static void submitCronJob(Runnable target, String cronExpression) {
    if (!init) {
      init();
    }

    try {
      JobDetail jobDetail = JobBuilder.newJob(SimpleProxyJob.class).build();
      jobDetail.getJobDataMap().put("RunObject", target);

      CronTrigger cronTrigger = TriggerBuilder.newTrigger()
          .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
          .build();

      scheduler.scheduleJob(jobDetail, cronTrigger);
    } catch (Exception e) {
      logger.error("", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * @param clz 根据“submitCronJob(Runnable target, String cronExpression)”方法的JavaDoc，clz需要满足两
   * 个条件：1）对应类需要继承java.io.Serializable接口；2）对应类需要对Quartz包可见且存在一个无参构造方法，这样Quartz包内部才能创建一个该类的实例对象
   */
  public static void submitCronJobSerializable(Class<? extends Job> clz, String cronExpression) {
    if (!init) {
      init();
    }

    try {
      JobDetail jobDetail = JobBuilder.newJob(clz).build();

      CronTrigger cronTrigger = TriggerBuilder.newTrigger()
          .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
          .build();

      scheduler.scheduleJob(jobDetail, cronTrigger);
    } catch (Exception e) {
      logger.error("", e);
      throw new RuntimeException(e);
    }
  }

  private static void init() {
    if (!init) {
      synchronized (QuartzScheduleAssist.class) {
        if (!init) {
          try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
          } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
          }
        }
      }
    }
  }
}
