package me.dslztx.assist.util;

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

    public static long timerValueAndClear(String name) {
        Map<String, TimerInterval> timerDetail = timer.get();

        if (timerDetail.get(name) == null) {
            return 0L;
        }

        Long value = timerDetail.get(name).value();

        timerDetail.remove(name);

        return value;
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

    public Long value() {
        if (start == -1 || stop == -1) {
            return null;
        } else {
            return stop - start;
        }
    }
}
