package me.dslztx.assist.bean;

import java.util.concurrent.atomic.AtomicLong;

//todo
public class CountAndAverageStat {

  private AtomicLong total = new AtomicLong(0);

  private AtomicLong success = new AtomicLong(0);

  private AtomicLong fail = new AtomicLong(0);

  private AtomicLong timeCost = new AtomicLong(0);

  public AtomicLong getTotal() {
    return total;
  }

  public void setTotal(AtomicLong total) {
    this.total = total;
  }

  public AtomicLong getSuccess() {
    return success;
  }

  public void setSuccess(AtomicLong success) {
    this.success = success;
  }

  public AtomicLong getFail() {
    return fail;
  }

  public void setFail(AtomicLong fail) {
    this.fail = fail;
  }

  public AtomicLong getTimeCost() {
    return timeCost;
  }

  public void setTimeCost(AtomicLong timeCost) {
    this.timeCost = timeCost;
  }

  public void incr() {
    total.incrementAndGet();
  }

  public void incrSuccess() {
    success.incrementAndGet();
  }

  public void incrFail() {
    fail.incrementAndGet();
  }

  public void addTimeCost(long timeCost) {
    this.timeCost.addAndGet(timeCost);
  }

  public void incrWithTimeCost(long timeCost) {
    synchronized (this) {
      total.incrementAndGet();
      this.timeCost.addAndGet(timeCost);
    }
  }

  public void incrSuccessWithTimeCost(long timeCost) {
    synchronized (this) {
      success.incrementAndGet();
      this.timeCost.addAndGet(timeCost);
    }
  }

  public void incrFailWithTimeCost(long timeCost) {
    synchronized (this) {
      fail.incrementAndGet();
      this.timeCost.addAndGet(timeCost);
    }
  }
}
