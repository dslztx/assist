package me.dslztx.assist.bean;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dslztx
 */
public class CountAndAverageStat {

  private AtomicLong total = new AtomicLong(0);

  private AtomicLong success = new AtomicLong(0);

  private AtomicLong fail = new AtomicLong(0);

  private AtomicLong timeCost = new AtomicLong(0);

  public AtomicLong getTotal() {
    return total;
  }

  public AtomicLong getSuccess() {
    return success;
  }

  public AtomicLong getFail() {
    return fail;
  }

  public AtomicLong getTimeCost() {
    return timeCost;
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
      this.total.incrementAndGet();
      this.timeCost.addAndGet(timeCost);
    }
  }

  public void incrSuccessWithTimeCost(long timeCost) {
    synchronized (this) {
      this.success.incrementAndGet();
      this.timeCost.addAndGet(timeCost);
    }
  }

  public void incrFailWithTimeCost(long timeCost) {
    synchronized (this) {
      this.fail.incrementAndGet();
      this.timeCost.addAndGet(timeCost);
    }
  }
}
