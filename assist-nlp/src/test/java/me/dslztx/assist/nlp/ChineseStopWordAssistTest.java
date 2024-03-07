package me.dslztx.assist.nlp;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChineseStopWordAssistTest {

    @Test
    public void isStopWord() {
        try {
            Assert.assertTrue(ChineseStopWordAssist.isStopWord("的"));
            Assert.assertTrue(ChineseStopWordAssist.isStopWord("。"));
            Assert.assertTrue(ChineseStopWordAssist.isStopWord("，"));
            Assert.assertTrue(ChineseStopWordAssist.isStopWord("？"));
            Assert.assertTrue(ChineseStopWordAssist.isStopWord("！"));
            Assert.assertTrue(ChineseStopWordAssist.isStopWord("《"));
            Assert.assertTrue(ChineseStopWordAssist.isStopWord("》"));
            Assert.assertFalse(ChineseStopWordAssist.isStopWord("计算机"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}