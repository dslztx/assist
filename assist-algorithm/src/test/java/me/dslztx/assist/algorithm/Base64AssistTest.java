package me.dslztx.assist.algorithm;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Base64AssistTest {

    @Test
    public void encodeBase64() {
        try {
            String s = "hello world";

            String ss = Base64Assist.encodeBase64(s.getBytes("UTF-8"));

            byte[] bb = Base64Assist.decodeBase64(ss);

            Assert.assertTrue(new String(bb, Charset.forName("UTF-8")).equals(s));

            Assert.assertNull(Base64Assist.encodeBase64(null));
            Assert.assertNull(Base64Assist.encodeBase64(new byte[0]));

        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void decodeBase64() {
        try {
            Assert.assertNull(Base64Assist.decodeBase64(""));
            Assert.assertNull(Base64Assist.decodeBase64(null));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}