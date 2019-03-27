package me.dslztx.assist.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 * @date 2015年08月11日
 */
public class CharCodingAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(CharCodingAssistTest.class);

    @Test
    public void encodeTest() {
        try {
            byte[] a = CharCodingAssist.encode("好", Charset.forName("UTF-8"));
            byte[] b = new byte[3];
            b[0] = (byte)0xe5;
            b[1] = (byte)0xa5;
            b[2] = (byte)0xbd;
            assertTrue(Arrays.equals(a, b));

            byte[] c = CharCodingAssist.encode("好", Charset.forName("GBK"));
            byte[] d = new byte[2];
            d[0] = (byte)0xba;
            d[1] = (byte)0xc3;
            assertTrue(Arrays.equals(c, d));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void decodeTest() {
        try {
            byte[] b = new byte[3];
            b[0] = (byte)0xe5;
            b[1] = (byte)0xa5;
            b[2] = (byte)0xbd;
            String str = CharCodingAssist.decode(b, Charset.forName("UTF-8"));
            assertTrue(str.equals("好"));

            byte[] d = new byte[2];
            d[0] = (byte)0xba;
            d[1] = (byte)0xc3;
            String str1 = CharCodingAssist.decode(d, Charset.forName("GBK"));
            assertTrue(str1.equals("好"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void charFromHTMLEscapeSequenceTest() {
        try {
            Assert.assertTrue(CharCodingAssist.charFromHTMLEscapeSequence("&#32;") == ' ');
            Assert.assertTrue(CharCodingAssist.charFromHTMLEscapeSequence("&#65;") == 'A');
            Assert.assertTrue(CharCodingAssist.charFromHTMLEscapeSequence("&#97;") == 'a');
            Assert.assertTrue(CharCodingAssist.charFromHTMLEscapeSequence("&#0097;") == 'a');
            Assert.assertTrue(CharCodingAssist.charFromHTMLEscapeSequence("&#000065;") == 'A');
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void charToHTMLEscapeSequenceTest() {
        try {
            Assert.assertTrue(CharCodingAssist.charToHTMLEscapeSequence('好').equals("&#22909;"));
            Assert.assertTrue(CharCodingAssist.charToHTMLEscapeSequence('a').equals("&#97;"));
            Assert.assertTrue(CharCodingAssist.charToHTMLEscapeSequence('A').equals("&#65;"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void escapeHTMLEscapeSequenceTest() {
        try {
            Assert.assertTrue(CharCodingAssist.escapeHTMLEscapeSequence("你好吗").equals("&#20320;&#22909;&#21527;"));

            Assert.assertTrue(CharCodingAssist.escapeHTMLEscapeSequence("http://www.google.com").equals(
                "&#104;&#116;&#116;&#112;&#58;&#47;&#47;&#119;&#119;&#119;&#46;&#103;&#111;&#111;&#103;&#108;&#101;&#46;&#99;&#111;&#109;"));

            Assert.assertTrue(CharCodingAssist.escapeHTMLEscapeSequence("http://www.alibaba.com").equals(
                "&#104;&#116;&#116;&#112;&#58;&#47;&#47;&#119;&#119;&#119;&#46;&#97;&#108;&#105;&#98;&#97;&#98;&#97;&#46;&#99;&#111;&#109;"));

            Assert.assertTrue(CharCodingAssist.escapeHTMLEscapeSequence("http://www.facebook.com").equals(
                "&#104;&#116;&#116;&#112;&#58;&#47;&#47;&#119;&#119;&#119;&#46;&#102;&#97;&#99;&#101;&#98;&#111;&#111;&#107;&#46;&#99;&#111;&#109;"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void unescapedHTMLEscapeSequenceTest() {
        try {
            String content =
                "&#104;&#116;&#116;&#112;&#58;&#47;&#47;&#119;&#119;&#119;&#46;&#102;&#97;&#99;&#101;&#98;&#111;&#111;&#107;&#46;&#99;&#111;&#109;";

            Assert.assertTrue(CharCodingAssist.unescapedHTMLEscapeSequence(content).equals("http://www.facebook.com"));

            Assert.assertTrue(CharCodingAssist.unescapedHTMLEscapeSequence(
                "h&#104;&#116;&#116;&#112;&#58;&#47;&#47;&#119;&#119;&#119;&#46;&#102;&#97;&#99;&#101;&#98;&#111;&#111;&#107;&#46;&#99;qllll&#111;&#109;m")
                .equals("hhttp://www.facebook.cqllllomm"));

            Assert.assertTrue(CharCodingAssist.unescapedHTMLEscapeSequence(
                "h&#104;&#116;&#116;&#112;&#58;&#47;&#47;&#119;&#119;&#119;&#46;&#102;&#97;&#99;&#101;&#98;&#111;&#111;&#107;&#46;&#99;qllll&#111;&#109;m&#109")
                .equals("hhttp://www.facebook.cqllllomm&#109"));

            Assert.assertTrue(CharCodingAssist.unescapedHTMLEscapeSequence(
                "h&#104;&#116;&#116;&#112;&#58;&#47;&#47;&#119;&#119;&#119;&#46;&#102;&#97;&#99;&#101;&#98;&#111;&#111;&#107;&#46;&#99;qllll&#111;&#109;m&#3")
                .equals("hhttp://www.facebook.cqllllomm&#3"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}
