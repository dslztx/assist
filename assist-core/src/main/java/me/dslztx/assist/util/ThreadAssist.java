package me.dslztx.assist.util;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadAssist {
    private static final Logger logger = LoggerFactory.getLogger(ThreadAssist.class);

    public static void sleep(long value, TimeUnit unit) {
        if (ObjectAssist.isNull(unit)) {
            throw new RuntimeException("time unit can not be null");
        }

        try {
            unit.sleep(value);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }
}
