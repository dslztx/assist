package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteShellAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteShellAssistTest.class);

    @Test
    public void executeShellSync() {
        try {
            TimerAssist.timerStart("execute shell");
            ExecuteShellAssist.executeShellSync("/bin/bash", "-c", "sleep 100s && echo 'hello world'");
            TimerAssist.timerStop("execute shell");

            Assert.assertTrue(TimerAssist.timerValue("execute shell").longValue() >= 100000L);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void executeShellAsync() {
        try {
            TimerAssist.timerStart("execute shell");
            ExecuteShellAssist.executeShellAsync("/bin/bash", "-c", "sleep 100s && echo 'hello world'");
            TimerAssist.timerStop("execute shell");

            Assert.assertTrue(TimerAssist.timerValue("execute shell").longValue() < 1000L);
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}