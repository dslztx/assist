package me.dslztx.assist.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IOAssistTest {

    @Test
    public void appendWriteTest() {
        try {
            BufferedWriter writer0 = IOAssist.bufferedWriter(new File("a.test"));
            writer0.write("hello");
            writer0.write("\n");

            writer0.close();

            BufferedWriter writer1 = IOAssist.bufferedWriter(new File("a.test"), true);
            writer1.write("world");
            writer1.write("\n");
            writer1.close();

            BufferedReader reader = IOAssist.bufferedReader(new File("a.test"));
            String line0 = reader.readLine();
            Assert.assertTrue("hello".equals(line0));

            String line1 = reader.readLine();
            Assert.assertTrue("world".equals(line1));

        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        } finally {
            new File("a.test").delete();
        }
    }
}