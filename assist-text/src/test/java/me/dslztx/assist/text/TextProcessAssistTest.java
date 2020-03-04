package me.dslztx.assist.text;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextProcessAssistTest {
    private static final Logger logger = LoggerFactory.getLogger(TextProcessAssistTest.class);

    @Test
    public void test0() {
        try {
            String content = "商场上行骗就能腰缠万贯。”　　请看 yun8.ga";

            Assert.assertTrue(TextProcessAssist.extractURLBasedOnSemantic(content).equals("yun8.ga"));

            String content1 = "晚退休、早入土。　　请看 yun8点gq";
            Assert.assertTrue(TextProcessAssist.extractURLBasedOnSemantic(content1).equals("yun8点gq"));


            String content2 = "讨债的帮凶？！　　请看 yun8点mla";
            Assert.assertTrue(TextProcessAssist.extractURLBasedOnSemantic(content2).equals("yun8点mla"));

        } catch (Exception e) {
            logger.error("", e);

            Assert.fail();
        }
    }

}