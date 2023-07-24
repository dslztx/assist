package me.dslztx.assist.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dslztx
 * @date 2015年08月18日
 */
@Slf4j
public class StringAssistTest {

    @org.junit.jupiter.api.Test
    public void testIsHexStr() throws Exception {
        try {
            assertTrue(StringAssist.isHexStr("aaFf"));
            assertFalse(StringAssist.isHexStr("zu"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void testIsOctStr() throws Exception {
        try {
            assertTrue(StringAssist.isOctStr("07770"));
            assertFalse(StringAssist.isOctStr("0768"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void testIsDecimalStr() throws Exception {
        try {
            assertTrue(StringAssist.isDecimalStr("3128390217"));
            assertFalse(StringAssist.isDecimalStr("983219z"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void testIsBlank() {
        try {
            assertTrue(StringAssist.isBlank(""));
            assertTrue(StringAssist.isBlank(null));
            assertTrue(StringAssist.isBlank("   "));
            assertFalse(StringAssist.isBlank("  fdsfd "));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void testToLowerCase() {
        try {
            assertNull(StringAssist.toLowerCase(null));
            assertTrue(StringAssist.toLowerCase("hEllo").equals("hello"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void testToUpperCase() {
        try {
            assertNull(StringAssist.toUpperCase(null));
            assertTrue(StringAssist.toUpperCase("hEllo").equals("HELLO"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void testSplit() {
        try {
            String s = "hello   world";

            Assert.assertTrue(StringAssist.split(s, ' ', false).length == 4);
            Assert.assertTrue(StringAssist.split(s, ' ', true).length == 2);

            String ss = ";;;;,,,;;;";
            Assert.assertTrue(StringAssist.split(ss, ';', false).length == 8);
            Assert.assertTrue(StringAssist.split(ss, ';', true).length == 1);
            Assert.assertTrue(StringAssist.split(ss, ',', false).length == 4);
            Assert.assertTrue(StringAssist.split(ss, ',', true).length == 2);

            String sss = "100,,53,";
            Assert.assertTrue(StringAssist.split(sss, ',', false).length == 4);
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void testRemoveChar() {
        try {
            String s = "abcd\r\n f gh\r\nfdss";
            Assert.assertTrue(StringAssist.removeChar(s, ' ', '\r', '\n').equals("abcdfghfdss"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void formatTest() {
        try {
            Assert.assertTrue(StringAssist.format("hello {} world", "a", "b").equals("hello a world"));
            Assert.assertTrue(StringAssist.format("hello {} world{", "a", "b").equals("hello a world{"));
            Assert.assertTrue(StringAssist.format("hello {} worl{fd", "a", "b").equals("hello a worl{fd"));
            Assert.assertTrue(StringAssist.format("hello {} worl{fd").equals("hello {} worl{fd"));

            Assert.assertTrue(StringAssist.format("hello {} world {}", "a").equals("hello a world {}"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void joinUseSeparatorTest() {
        try {
            Assert.assertNull(StringAssist.joinUseSeparator('#'));
            Assert.assertTrue(StringAssist.joinUseSeparator('#', "").equals(""));
            Assert.assertTrue(StringAssist.joinUseSeparator('#', "", null).equals("#NULL"));
            Assert.assertTrue(StringAssist.joinUseSeparator('#', null, "").equals("NULL#"));
            Assert.assertTrue(StringAssist.joinUseSeparator('#', "", "").equals("#"));
            Assert.assertTrue(StringAssist.joinUseSeparator('#', "hello", "world").equals("hello#world"));
            Assert.assertTrue(StringAssist.joinUseSeparator('#', 1, 2, 3, null, "hel").equals("1#2#3#NULL#hel"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void splitFirstPartTest() {
        try {
            Assert.assertTrue(StringAssist.splitFirstPart("hello_world", '_').equals("hello"));
            Assert.assertTrue(StringAssist.splitFirstPart("hello", '_').equals("hello"));
            Assert.assertTrue(StringAssist.splitFirstPart("", '_').equals(""));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void splitLastPartTest() {
        try {
            Assert.assertTrue(StringAssist.splitLastPart("hello_world", '_').equals("world"));
            Assert.assertTrue(StringAssist.splitLastPart("hello", '_').equals("hello"));
            Assert.assertTrue(StringAssist.splitLastPart("", '_').equals(""));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void truncateWhitespaceTest() {
        try {
            Assert.assertTrue(StringAssist.truncateWhitespace("     ").equals(""));
            Assert.assertTrue(StringAssist.truncateWhitespace(" he \r\n\t llo  ").equals("hello"));
            Assert.assertTrue(StringAssist.truncateWhitespace("hello world").equals("helloworld"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void concatTest() {
        try {
            List<String> s = new ArrayList<String>();
            s.add("hello");
            s.add("world");

            Assert.assertTrue(StringAssist.concat(s).equals("helloworld"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void allPossibleSuffixesAtLeastNPartsTest() {
        try {
            Assert.assertTrue(StringAssist.allPossibleSuffixesAtLeastNParts(null, 3, '.').size() == 0);
            Assert.assertTrue(StringAssist.allPossibleSuffixesAtLeastNParts(new String[0], 3, '.').size() == 0);
            Assert.assertTrue(
                StringAssist.allPossibleSuffixesAtLeastNParts(new String[] {"hello", "world"}, 3, '.').size() == 0);

            List<String> result =
                StringAssist.allPossibleSuffixesAtLeastNParts(new String[] {"sina", "baidu", "com", "cn"}, 2, '.');
            Assert.assertTrue(result.size() == 3);
            Assert.assertTrue(result.get(0).equals("com.cn"));
            Assert.assertTrue(result.get(1).equals("baidu.com.cn"));
            Assert.assertTrue(result.get(2).equals("sina.baidu.com.cn"));

        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void removeControlCharsTest() {
        try {
            String s =
                "ab" + "\u200bcd" + "\u200cef" + "\u200dgh" + "\u200fij" + "\u2029k\u202al\u202bm\u202cn\u202do\u202ep"
                    + "\u2061qr\u2062s\u2063t\u2064uv\u206aw\u206bx\u206cy\u206dz\u206f";
            Assert.assertTrue("abcdefghijklmnopqrstuvwxyz".equals(StringAssist.removeControlChars(s)));

            String s3 = "\u202e你\u202d好\u202e人生\u202eabcd\u202eefghi\u202ejk202dl\u202dfff\u202em\u202dn";
            Assert.assertTrue("你好人生abcdefghijk202dlfffmn".equals(StringAssist.removeControlChars(s3)));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void processTextOrderCharsTest() {
        try {
            String s = "\u202e你\u202d好人生";
            Assert.assertTrue("好人生你".equals(StringAssist.processTextOrderChars(s)));

            String s1 = "\u202e\u202d";
            Assert.assertTrue("".equals(StringAssist.processTextOrderChars(s1)));

            String s2 = "你\u202e好人生";
            Assert.assertTrue("你生人好".equals(StringAssist.processTextOrderChars(s2)));

            String s3 = "\u202e你\u202d好\u202e人生\u202eabcd\u202eefghi\u202ejk202dl\u202dfff\u202em\u202dn";
            Assert.assertTrue("好fffnmld202kjihgfedcba生人你".equals(StringAssist.processTextOrderChars(s3)));

            String s4 = "PO358\u202evaw.htm]";
            // 实际上打印出来的是"PO358[mth.wav"，暂时不考虑
            Assert.assertTrue("PO358]mth.wav".equals(StringAssist.processTextOrderChars(s4)));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void obtainLengthDefaultZeroTest() {
        try {
            Assert.assertTrue(StringAssist.obtainLengthDefaultZero(null) == 0);
            Assert.assertTrue(StringAssist.obtainLengthDefaultZero("") == 0);
            Assert.assertTrue(StringAssist.obtainLengthDefaultZero("hello") == 5);
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }
}
