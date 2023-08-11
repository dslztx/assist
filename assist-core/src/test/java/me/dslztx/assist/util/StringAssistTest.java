package me.dslztx.assist.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StringAssistTest {

    @BeforeAll
    public static void tt0() {
        System.out.println("0");
    }

    @AfterAll
    public static void ee0() {
        System.out.println("1000");
    }

    @Test
    @Order(1)
    public void testIsHexStr() throws Exception {
        try {
            System.out.println(1);

            assertTrue(StringAssist.isHexStr("aaFf"));
            assertFalse(StringAssist.isHexStr("zu"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    @Order(3)
    public void testIsOctStr() throws Exception {
        try {
            System.out.println(3);

            assertTrue(StringAssist.isOctStr("07770"));
            assertFalse(StringAssist.isOctStr("0768"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    @Order(2)
    public void testIsDecimalStr() throws Exception {
        try {
            System.out.println(2);

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

            assertTrue(StringAssist.split(s, ' ', false).length == 4);
            assertTrue(StringAssist.split(s, ' ', true).length == 2);

            String ss = ";;;;,,,;;;";
            assertTrue(StringAssist.split(ss, ';', false).length == 8);
            assertTrue(StringAssist.split(ss, ';', true).length == 1);
            assertTrue(StringAssist.split(ss, ',', false).length == 4);
            assertTrue(StringAssist.split(ss, ',', true).length == 2);

            String sss = "100,,53,";
            assertTrue(StringAssist.split(sss, ',', false).length == 4);
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void testRemoveChar() {
        try {
            String s = "abcd\r\n f gh\r\nfdss";
            assertTrue(StringAssist.removeChar(s, ' ', '\r', '\n').equals("abcdfghfdss"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void formatTest() {
        try {
            assertTrue(StringAssist.format("hello {} world", "a", "b").equals("hello a world"));
            assertTrue(StringAssist.format("hello {} world{", "a", "b").equals("hello a world{"));
            assertTrue(StringAssist.format("hello {} worl{fd", "a", "b").equals("hello a worl{fd"));
            assertTrue(StringAssist.format("hello {} worl{fd").equals("hello {} worl{fd"));

            assertTrue(StringAssist.format("hello {} world {}", "a").equals("hello a world {}"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void joinUseSeparatorTest() {
        try {
            assertNull(StringAssist.joinUseSeparator('#'));
            assertTrue(StringAssist.joinUseSeparator('#', "").equals(""));
            assertTrue(StringAssist.joinUseSeparator('#', "", null).equals("#NULL"));
            assertTrue(StringAssist.joinUseSeparator('#', null, "").equals("NULL#"));
            assertTrue(StringAssist.joinUseSeparator('#', "", "").equals("#"));
            assertTrue(StringAssist.joinUseSeparator('#', "hello", "world").equals("hello#world"));
            assertTrue(StringAssist.joinUseSeparator('#', 1, 2, 3, null, "hel").equals("1#2#3#NULL#hel"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void splitFirstPartTest() {
        try {
            assertTrue(StringAssist.splitFirstPart("hello_world", '_').equals("hello"));
            assertTrue(StringAssist.splitFirstPart("hello", '_').equals("hello"));
            assertTrue(StringAssist.splitFirstPart("", '_').equals(""));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void splitLastPartTest() {
        try {
            assertTrue(StringAssist.splitLastPart("hello_world", '_').equals("world"));
            assertTrue(StringAssist.splitLastPart("hello", '_').equals("hello"));
            assertTrue(StringAssist.splitLastPart("", '_').equals(""));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void truncateWhitespaceTest() {
        try {
            assertTrue(StringAssist.truncateWhitespace("     ").equals(""));
            assertTrue(StringAssist.truncateWhitespace(" he \r\n\t llo  ").equals("hello"));
            assertTrue(StringAssist.truncateWhitespace("hello world").equals("helloworld"));
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

            assertTrue(StringAssist.concat(s).equals("helloworld"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void allPossibleSuffixesAtLeastNPartsTest() {
        try {
            assertTrue(StringAssist.allPossibleSuffixesAtLeastNParts(null, 3, '.').size() == 0);
            assertTrue(StringAssist.allPossibleSuffixesAtLeastNParts(new String[0], 3, '.').size() == 0);
            assertTrue(
                    StringAssist.allPossibleSuffixesAtLeastNParts(new String[]{"hello", "world"}, 3, '.').size() == 0);

            List<String> result =
                    StringAssist.allPossibleSuffixesAtLeastNParts(new String[]{"sina", "baidu", "com", "cn"}, 2, '.');
            assertTrue(result.size() == 3);
            assertTrue(result.get(0).equals("com.cn"));
            assertTrue(result.get(1).equals("baidu.com.cn"));
            assertTrue(result.get(2).equals("sina.baidu.com.cn"));

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
            assertTrue("abcdefghijklmnopqrstuvwxyz".equals(StringAssist.removeControlChars(s)));

            String s3 = "\u202e你\u202d好\u202e人生\u202eabcd\u202eefghi\u202ejk202dl\u202dfff\u202em\u202dn";
            assertTrue("你好人生abcdefghijk202dlfffmn".equals(StringAssist.removeControlChars(s3)));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void processTextOrderCharsTest() {
        try {
            String s = "\u202e你\u202d好人生";
            assertTrue("好人生你".equals(StringAssist.processTextOrderChars(s)));

            String s1 = "\u202e\u202d";
            assertTrue("".equals(StringAssist.processTextOrderChars(s1)));

            String s2 = "你\u202e好人生";
            assertTrue("你生人好".equals(StringAssist.processTextOrderChars(s2)));

            String s3 = "\u202e你\u202d好\u202e人生\u202eabcd\u202eefghi\u202ejk202dl\u202dfff\u202em\u202dn";
            assertTrue("好fffnmld202kjihgfedcba生人你".equals(StringAssist.processTextOrderChars(s3)));

            String s4 = "PO358\u202evaw.htm]";
            // 实际上打印出来的是"PO358[mth.wav"，暂时不考虑
            assertTrue("PO358]mth.wav".equals(StringAssist.processTextOrderChars(s4)));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void obtainLengthDefaultZeroTest() {
        try {
            assertTrue(StringAssist.obtainLengthDefaultZero(null) == 0);
            assertTrue(StringAssist.obtainLengthDefaultZero("") == 0);
            assertTrue(StringAssist.obtainLengthDefaultZero("hello") == 5);
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }
}
