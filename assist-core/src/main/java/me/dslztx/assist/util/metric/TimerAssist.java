package me.dslztx.assist.util.metric;

import java.util.HashMap;
import java.util.Map;

public class TimerAssist {

    private static ThreadLocal<Map<String, TimerInterval>> timer = new ThreadLocal<Map<String, TimerInterval>>() {
        public Map<String, TimerInterval> initialValue() {
            return new HashMap<String, TimerInterval>();
        }
    };

    public static void timerStart(String name) {
        Map<String, TimerInterval> timerDetail = timer.get();

        if (timerDetail.get(name) == null) {
            timerDetail.put(name, new TimerInterval());
        }

        timerDetail.get(name).start();
    }

    public static void timerStop(String name) {
        Map<String, TimerInterval> timerDetail = timer.get();

        if (timerDetail.get(name) == null) {
            timerDetail.put(name, new TimerInterval());
        }

        timerDetail.get(name).stop();
    }

    /**
     * 获取值并clear版本，防止内存泄漏
     */
    public static long timerValueAndClear(String name) {
        Map<String, TimerInterval> timerDetail = timer.get();

        if (timerDetail.get(name) == null) {
            return 0L;
        }

        long value = timerDetail.get(name).value();

        timerDetail.remove(name);

        return value;
    }

    public static long timerValue(String name) {
        Map<String, TimerInterval> timerDetail = timer.get();

        if (timerDetail.get(name) == null) {
            return 0L;
        }

        return timerDetail.get(name).value();
    }
}

class TimerInterval {
    long start = -1;
    long stop = -1;

    public void start() {
        this.start = System.currentTimeMillis();
    }

    public void stop() {
        this.stop = System.currentTimeMillis();
    }

    public long value() {
        if (start == -1 || stop == -1) {
            return 0L;
        } else {
            return stop - start;
        }
    }
}
