package me.dslztx.assist.text;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HalfWidthFullWidthAssistTest {

    @Test
    public void test0() {
        try {
            Assert.assertTrue("ａｂｃｄｅｆｇ　你好".equals(HalfWidthFullWidthAssist.halfWidth2FullWidth("abcdefg 你好")));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void test1() {
        try {
            Assert.assertTrue("abcdefg 你好".equals(HalfWidthFullWidthAssist.fullWidth2HalfWidth("ａｂｃｄｅｆｇ　你好")));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}