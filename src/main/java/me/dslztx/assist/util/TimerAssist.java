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

    public static Long timerValue(String name) {
        Map<String, TimerInterval> timerDetail = timer.get();

        if (timerDetail.get(name) == null) {
            return null;
        }

        return timerDetail.get(name).value();
    }

    public static String timerPrint() {
        StringBuilder sb = new StringBuilder();
        Map<String, TimerInterval> timerDetail = timer.get();
        for (String key : timerDetail.keySet()) {
            sb.append("[");
            sb.append(key);
            sb.append("]");
            sb.append(timerDetail.get(key).value());
        }
        return sb.toString();
    }

    public static void timerClear() {
        Map<String, TimerInterval> timerDetail = timer.get();
        timerDetail.keySet().clear();
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
