package me.dslztx.assist.nlp;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChineseSegmentAssistTest {

    @Test
    public void segmentBaseOn() {
        try {
            String text =
                "美利坚合众国，简称合众国或美国，是位于北美洲的联邦制共和国，亦是当今世上唯一的超级大国，由50个州加上首都华盛顿哥伦比亚特区、五个离岸自治领土及若干外岛组成。美国的官方领土面积为9,372,610平方千米，为北美第二，并位居世界第三或第四。";

            List<String> result = ChineseSegmentAssist.segmentBasedOnDict(text, true);
            Assert.assertTrue(result.contains("美利坚合众国"));
            Assert.assertTrue(result.contains("唯一"));
            Assert.assertTrue(result.contains("超级大国"));

            List<String> result2 = ChineseSegmentAssist.segmentBasedOnDict(text, false);

            Assert.assertTrue(result2.contains("美利坚合众国"));
            Assert.assertTrue(result2.contains("唯一"));
            Assert.assertTrue(result2.contains("超级大国"));
            Assert.assertTrue(result2.contains("，"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}