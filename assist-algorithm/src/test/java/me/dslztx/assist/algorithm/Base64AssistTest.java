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

    @Test
    public void judLegalTest() {
        try {
            Assert.assertTrue(Base64Assist.isLegalBase64Char(""));
            Assert.assertTrue(Base64Assist.isLegalBase64Char(null));

            Assert.assertFalse(Base64Assist.isLegalBase64Char("asb"));

            Assert.assertTrue(Base64Assist.isLegalBase64Char("asbb"));

            Assert.assertFalse(Base64Assist.isLegalBase64Char("asbI@@@@"));

            Assert.assertFalse(Base64Assist
                .isLegalBase64Char("IA==109947725a4053f2f0c572547de13b0cDX@109947725a4053f2f0c572547de 13b0cDX@DXDX@"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void judgeLegalLenTest() {
        try {
            Assert.assertTrue(Base64Assist.isLegalBase64Len(""));
            Assert.assertTrue(Base64Assist.isLegalBase64Len(null));

            Assert.assertFalse(Base64Assist.isLegalBase64Len(" "));
            Assert.assertTrue(Base64Assist.isLegalBase64Len("asdb"));

            Assert.assertFalse(Base64Assist.isLegalBase64Len("asdbb"));

        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}