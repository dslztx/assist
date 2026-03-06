package me.dslztx.assist.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharAssistTest {
    private static final Logger logger = LoggerFactory.getLogger(CharAssistTest.class);

    @Test
    public void testIsHexChar() throws Exception {
        try {
            assertTrue(CharAssist.isHexChar('f'));
            assertFalse(CharAssist.isHexChar('z'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsOctChar() throws Exception {
        try {
            assertTrue(CharAssist.isOctChar('7'));
            assertFalse(CharAssist.isOctChar('8'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDecimalChar() throws Exception {
        try {
            assertTrue(CharAssist.isDecimalChar('9'));
            assertFalse(CharAssist.isDecimalChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDigitChar() throws Exception {
        try {
            assertTrue(CharAssist.isDigitChar('9'));
            assertFalse(CharAssist.isDigitChar('a'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsChineseChar() {
        try {
            assertTrue(CharAssist.isChineseChar('好'));
            assertFalse(CharAssist.isChineseChar('》'));
            assertFalse(CharAssist.isChineseChar('>'));
            assertFalse(CharAssist.isChineseChar('a'));
            assertFalse(CharAssist.isChineseChar('1'));
            assertFalse(CharAssist.isChineseChar('\r'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsEnglishChar() {
        try {
            assertFalse(CharAssist.isEnglishChar('》'));
            assertFalse(CharAssist.isEnglishChar('〸'));
            assertFalse(CharAssist.isEnglishChar('>'));
            assertFalse(CharAssist.isEnglishChar('='));
            assertTrue(CharAssist.isEnglishChar('a'));
            assertFalse(CharAssist.isEnglishChar('好'));
            assertFalse(CharAssist.isEnglishChar('\r'));
            assertFalse(CharAssist.isEnglishChar('1'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsChinesePunctuation() {
        try {
            assertTrue(CharAssist.isChinesePunctuation('》'));
            assertTrue(CharAssist.isChinesePunctuation('〸'));
            assertFalse(CharAssist.isChinesePunctuation('>'));
            assertFalse(CharAssist.isChinesePunctuation('='));
            assertFalse(CharAssist.isChinesePunctuation('a'));
            assertFalse(CharAssist.isChinesePunctuation('好'));
            assertFalse(CharAssist.isChinesePunctuation('\r'));
            assertFalse(CharAssist.isChinesePunctuation('1'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void testIsEnglishPunctuation() {
        try {
            assertFalse(CharAssist.isEnglishPunctuation('》'));
            assertFalse(CharAssist.isEnglishPunctuation('〸'));
            assertTrue(CharAssist.isEnglishPunctuation('>'));
            assertTrue(CharAssist.isEnglishPunctuation('='));
            assertFalse(CharAssist.isEnglishPunctuation('a'));
            assertFalse(CharAssist.isEnglishPunctuation('好'));
            assertFalse(CharAssist.isEnglishPunctuation('\r'));
            assertFalse(CharAssist.isEnglishPunctuation('1'));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void isControlCharTest() {
        try {

            Assert.assertTrue(CharAssist.isControlChar((char)0x202e));
            Assert.assertTrue(CharAssist.isControlChar((char)0x1f));
            Assert.assertTrue(CharAssist.isControlChar((char)0xfeff));
            Assert.assertTrue(CharAssist.isControlChar((char)0x200f));
            Assert.assertTrue(CharAssist.isControlChar((char)0x00ad));
            Assert.assertTrue(CharAssist.isControlChar((char)0x200b));

            Assert.assertFalse(CharAssist.isControlChar((char)0x6004));

            String s = "𝗷𝕫𝘅𝕫119.𝗰𝗻";

            char c;
            // System.out.println(s.length());

            System.out.println(s.codePointAt(0));

            System.out.println(s.charAt(0));

            for (int index = 0; index < s.length(); index++) {
                c = s.charAt(index);

                System.out.println(Character.getType(c));
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void removeInvisibleCharactersTest() {
        try {

            Assert.assertTrue("".equals(CharAssist.removeInvisibleCharacters("")));
            Assert.assertNull(CharAssist.removeInvisibleCharacters(null));

            Assert.assertTrue("hello".equals(CharAssist.removeInvisibleCharacters("hello")));

            Assert.assertTrue("".equals(CharAssist.removeInvisibleCharacters("\r\n\t")));

            Assert.assertTrue("hello".equals(CharAssist.removeInvisibleCharacters("he\r\nllo\r\b\u001f\u008f")));

            Assert.assertTrue("hello".equals(CharAssist.removeInvisibleCharacters("he\u200Bllo\u200C\u200D\uFEFF")));

            Assert.assertTrue("hello".equals(CharAssist.removeInvisibleCharacters("hello\u200E")));

            Assert.assertTrue(
                "https://rankoncores.my./q/XCTRVYervwci&ervwci&34=Z2NAY2hlbXNoaW5lLWdyb3VwLmNvbQ#c3995725ba6d9166d78e9ca24caaf86522944e4e6969124d9fe366644dc7bc1d67399966ce8b20c7794d2f4b72d6e3662451ad79bac9b1ce0760"
                    .equals(CharAssist.removeInvisibleCharacters(
                        "https://ra\u180Fnko\u180Fnco\u180Fres.m\u180Fy./q/XCTRVYervwci&ervwci&34=Z2NAY2hlbXNoaW5lLWdyb3VwLmNvbQ#c3995725ba6d9166d78e9ca24caaf86522944e4e6969124d9fe366644dc7bc1d67399966ce8b20c7794d2f4b72d6e3662451ad79bac9b1ce0760")));

            Assert.assertTrue(
                "https://rankoncores.my./q/ZUDARVcbltzh&cbltzh&34=aW5mb0BzdG9uZW1hcmt0LmNvbQ#67a41b44cb80e063e5848320380e24f8475ebaf9ecd4d6e8216d427a25684af98baad1e8ed98f2a274f3c12aa4d5de9e55d76ec16cce862569cc8"
                    .equals(CharAssist.removeInvisibleCharacters(
                        "https://ra᠍᠌nko\u180Fnco᠍᠌res.m᠍᠌y./q/ZUDARVcbltzh&cbltzh&34=aW5mb0BzdG9uZW1hcmt0LmNvbQ#67a41b44cb80e063e5848320380e24f8475ebaf9ecd4d6e8216d427a25684af98baad1e8ed98f2a274f3c12aa4d5de9e55d76ec16cce862569cc8")));

            Assert.assertTrue(("https://rankoncores.my/q/HAZIJOvuqpcn&vuqpcn&-=Y3BwQHNjZW5ld2F5LmNu#åä ̧æ1å1⁄4äoåä "
                + "̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä "
                + "̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4")
                    .equals(CharAssist.removeInvisibleCharacters(
                        "https://ra᠍᠌nko᠍᠌nco᠍᠌res.m᠍᠌y/q/HAZIJOvuqpcn&vuqpcn&-=Y3BwQHNjZW5ld2F5LmNu#åä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4")));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void existInvisibleCharacterTest() {
        try {

            Assert.assertTrue(CharAssist.existInvisibleCharacter(
                "https://ra\u180Fnko\u180Fnco\u180Fres.m\u180Fy./q/XCTRVYervwci&ervwci&34=Z2NAY2hlbXNoaW5lLWdyb3VwLmNvbQ#c3995725ba6d9166d78e9ca24caaf86522944e4e6969124d9fe366644dc7bc1d67399966ce8b20c7794d2f4b72d6e3662451ad79bac9b1ce0760"));

            Assert.assertTrue(CharAssist.existInvisibleCharacter(
                "https://ra᠍᠌nko\u180Fnco᠍᠌res.m᠍᠌y./q/ZUDARVcbltzh&cbltzh&34=aW5mb0BzdG9uZW1hcmt0LmNvbQ#67a41b44cb80e063e5848320380e24f8475ebaf9ecd4d6e8216d427a25684af98baad1e8ed98f2a274f3c12aa4d5de9e55d76ec16cce862569cc8"));
            Assert.assertTrue(CharAssist.existInvisibleCharacter(
                "https://ra᠍᠌nko᠍᠌nco᠍᠌res.m᠍᠌y/q/HAZIJOvuqpcn&vuqpcn&-=Y3BwQHNjZW5ld2F5LmNu#åä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4äoåä ̧æ1å1⁄4"));

            Assert.assertFalse(CharAssist.existInvisibleCharacter("hello world"));
            Assert.assertFalse(CharAssist.existInvisibleCharacter("http://www.baidu.com"));
            Assert.assertFalse(CharAssist.existInvisibleCharacter("http://www.google.com"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}
