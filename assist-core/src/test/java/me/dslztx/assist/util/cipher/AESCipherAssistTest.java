package me.dslztx.assist.util.cipher;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.ClassPathResourceAssist;

@Slf4j
public class AESCipherAssistTest {

    @Test
    public void test0() {
        try {
            int count = 10;
            while ((count--) > 0) {
                String s =
                    FileUtils.readFileToString(ClassPathResourceAssist.locateFileNotInJar("cipher.sample"), "utf-8");

                byte[] cipherData = AESCipherAssist.encrypt(s.getBytes("utf-8"));

                byte[] decipherData = AESCipherAssist.decrypt(cipherData);

                Assert.assertTrue(new String(decipherData, "utf-8").equals(s));
            }
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}