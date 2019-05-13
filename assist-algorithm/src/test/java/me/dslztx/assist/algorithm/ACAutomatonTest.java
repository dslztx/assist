package me.dslztx.assist.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ACAutomatonTest {

    private static final Logger logger = LoggerFactory.getLogger(ACAutomatonTest.class);

    @Test
    public void hitTest() {
        try {
            List<String> keywordList = new ArrayList<String>();
            keywordList.add("hers");
            keywordList.add("his");
            keywordList.add("she");
            keywordList.add("he");

            ACAutomaton automaton = new ACAutomaton(keywordList);

            List<PatternHit> patternHitList = automaton.hit("uhers");

            Assert.assertTrue(patternHitList.get(0).toString().equals("[1:3]=he"));
            Assert.assertTrue(patternHitList.get(1).toString().equals("[1:5]=hers"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}