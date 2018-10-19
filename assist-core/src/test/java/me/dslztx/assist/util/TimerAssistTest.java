package me.dslztx.assist.util;

import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class TimerAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(TimerAssistTest.class);

    @Test
    public void test1() {
        try {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimerAssist.timerStart("P1");
                        TimeUnit.MILLISECONDS.sleep(400);
                        TimerAssist.timerStop("P1");

                        TimerAssist.timerStart("P2");
                        TimeUnit.MILLISECONDS.sleep(500);
                        TimerAssist.timerStop("P2");

                        System.out.println(TimerAssist.timerPrint());

                        TimerAssist.timerStart("P1");
                        TimeUnit.MILLISECONDS.sleep(600);
                        TimerAssist.timerStop("P1");
                        System.out.println(TimerAssist.timerPrint());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            });
            thread1.start();

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimerAssist.timerStart("P1");
                        TimeUnit.MILLISECONDS.sleep(500);
                        TimerAssist.timerStop("P1");

                        System.out.println(TimerAssist.timerPrint());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            });
            thread2.start();

            TimeUnit.MINUTES.sleep(1);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void test2() {
        try {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimerAssist.timerStart("P1");
                        TimeUnit.MILLISECONDS.sleep(400);
                        TimerAssist.timerStop("P1");

                        TimerAssist.timerStart("P2");
                        TimeUnit.MILLISECONDS.sleep(500);
                        TimerAssist.timerStop("P2");

                        System.out.println(TimerAssist.timerPrint());
                        TimerAssist.timerClear();

                        TimerAssist.timerStart("P1");
                        TimeUnit.MILLISECONDS.sleep(600);
                        TimerAssist.timerStop("P1");
                        System.out.println(TimerAssist.timerPrint());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            });
            thread1.start();

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimerAssist.timerStart("P1");
                        TimeUnit.MILLISECONDS.sleep(500);
                        TimerAssist.timerStop("P1");

                        System.out.println(TimerAssist.timerPrint());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            });
            thread2.start();

            TimeUnit.MINUTES.sleep(1);
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}