package me.dslztx.assist.util;

import java.util.concurrent.ConcurrentHashMap;
import me.dslztx.assist.bean.CountAndAverageStat;

/**
 * @author dslztx
 */
public class CountAndAverageStatAssist {

  private static ConcurrentHashMap<String, CountAndAverageStat> stats = new ConcurrentHashMap<String, CountAndAverageStat>();

  public static void incr(String name) {
    stats.putIfAbsent(name, new CountAndAverageStat());

    CountAndAverageStat stat = stats.get(name);
    if (stat != null) {
      stat.incr();
    }
  }

  public static void incrSuccess(String name) {
    stats.putIfAbsent(name, new CountAndAverageStat());

    CountAndAverageStat stat = stats.get(name);
    if (stat != null) {
      stat.incrSuccess();
    }
  }

  public static void incrFail(String name) {
    stats.putIfAbsent(name, new CountAndAverageStat());

    CountAndAverageStat stat = stats.get(name);
    if (stat != null) {
      stat.incrFail();
    }
  }

  public static void addTimeCost(String name, long timeCost) {
    stats.putIfAbsent(name, new CountAndAverageStat());

    CountAndAverageStat stat = stats.get(name);
    if (stat != null) {
      stat.addTimeCost(timeCost);
    }
  }

  public static void incrWithTimeCost(String name, long timeCost) {
    stats.putIfAbsent(name, new CountAndAverageStat());

    CountAndAverageStat stat = stats.get(name);
    if (stat != null) {
      stat.incrWithTimeCost(timeCost);
    }
  }

  public static void incrSuccessWithTimeCost(String name, long timeCost) {
    stats.putIfAbsent(name, new CountAndAverageStat());

    CountAndAverageStat stat = stats.get(name);
    if (stat != null) {
      stat.incrSuccessWithTimeCost(timeCost);
    }
  }

  public static void incrFailWithTimeCost(String name, long timeCost) {
    stats.putIfAbsent(name, new CountAndAverageStat());

    CountAndAverageStat stat = stats.get(name);
    if (stat != null) {
      stat.incrFailWithTimeCost(timeCost);
    }
  }

  public static CountAndAverageStat obtainAndClear(String name) {
    CountAndAverageStat stat = stats.get(name);
    stats.remove(name);
    return stat;
  }
}
