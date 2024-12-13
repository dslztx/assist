package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhoneAssistTest {

    @Test
    public void test0() {
        try {
            Assert.assertTrue(PhoneAssist.isPhone("13012345678"));
            Assert.assertFalse(PhoneAssist.isPhone(null));
            Assert.assertFalse(PhoneAssist.isPhone(""));
            Assert.assertFalse(PhoneAssist.isPhone("134567893011"));
            Assert.assertFalse(PhoneAssist.isPhone("34567893011"));
            Assert.assertFalse(PhoneAssist.isPhone("14a67893011"));
        } catch (Exception e) {
            log.error("", e);

            Assert.fail();
        }
    }

    @Test
    public void test1() {
        try {
            Assert.assertTrue(PhoneAssist.isPhoneRegex("13012345678"));
            Assert.assertTrue(PhoneAssist.isPhoneRegex("19517893011"));
            Assert.assertTrue(PhoneAssist.isPhoneRegex("15317893011"));
            Assert.assertFalse(PhoneAssist.isPhoneRegex(null));
            Assert.assertFalse(PhoneAssist.isPhoneRegex(""));
            Assert.assertFalse(PhoneAssist.isPhoneRegex("134567893011"));
            Assert.assertFalse(PhoneAssist.isPhoneRegex("34567893011"));
            Assert.assertFalse(PhoneAssist.isPhoneRegex("15467893011"));
            Assert.assertFalse(PhoneAssist.isPhoneRegex("16367893011"));
            Assert.assertFalse(PhoneAssist.isPhoneRegex("19417893011"));
            Assert.assertFalse(PhoneAssist.isPhoneRegex("14a67893011"));
            Assert.assertFalse(PhoneAssist.isPhoneRegex("14a67893011"));
        } catch (Exception e) {
            log.error("", e);

            Assert.fail();
        }
    }
}