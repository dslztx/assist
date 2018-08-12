package me.dslztx.assist.bean;

import java.io.Serializable;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据QuartzScheduleAssist类中“submitCronJob(Runnable target, String cronExpression)”方法的JavaDoc，
 * SimpleProxyJob需要满足两个条件：1）需要继承java.io.Serializable接口；2）需要对Quartz包可见且存在一个无参构造方法，这样Quartz包内部才能创建一个该类的实例对象
 */
public class SimpleProxyJob implements Job, Serializable {

  private static final Logger logger = LoggerFactory.getLogger(SimpleProxyJob.class);

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    Runnable target = null;
    try {
      target = (Runnable) jobExecutionContext.getMergedJobDataMap().get("RunObject");
      if (target == null) {
        throw new RuntimeException("no run task");
      }
      target.run();
    } catch (Exception e) {
      logger.error("", e);
      throw new JobExecutionException(e);
    }
  }
}
