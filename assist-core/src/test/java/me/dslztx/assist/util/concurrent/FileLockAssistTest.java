package me.dslztx.assist.util.concurrent;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileLockAssistTest {

    @Test
    public void lockTest() {
        try {

            File file = new File("/home/dslztx/Desktop");

            A a = new A();
            a.setValue(10);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileLockAssist.tryLock(file);
                    FileLockAssist.tryLock(file);
                    FileLockAssist.tryRelease(file);
                    a.setValue(20);
                }
            }).start();

            Thread.sleep(10000L);

            Assert.assertFalse(FileLockAssist.tryLock(file));
            Assert.assertTrue(a.getValue() == 20);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}

@Data
class A {
    int value;
}