package me.dslztx.assist.text;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HTMLEscapeAssistTest {

    @Test
    public void test0() {
        try {

            String input0 = "&#38;&#x26;&#38;&#38;&#39;&#969;";
            Assert.assertTrue(HTMLEscapeAssist.unescape(input0).equalsIgnoreCase("&&&&'ω"));

            String input1 = "&amp;lt;h1&amp;gt; hello &amp;amp; world&amp;lt;/h1&amp;gt;&amp;amp;&amp;amp;";
            Assert.assertTrue(HTMLEscapeAssist.unescape(input1)
                .equalsIgnoreCase("&lt;h1&gt; hello &amp; world&lt;/h1&gt;&amp;&amp;"));

        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

    @Test
    public void test1() {
        try {

            String text0 = "&#38;&#x26;&#38;&#38;&#39;&#969;";
            Assert.assertTrue(
                HTMLEscapeAssist.escape(text0).equals("&amp;#38;&amp;#x26;&amp;#38;&amp;#38;&amp;#39;&amp;#969;"));

            String text1 = "&amp;lt;h1&amp;gt; hello &amp;amp; world&amp;lt;/h1&amp;gt;&amp;amp;&amp;amp;";
            Assert.assertTrue(HTMLEscapeAssist.escape(text1).equals(
                "&amp;amp;lt;h1&amp;amp;gt; hello &amp;amp;amp; world&amp;amp;lt;/h1&amp;amp;gt;&amp;amp;amp;&amp;amp;amp;"));

        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }
}