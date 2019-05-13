package me.dslztx.assist.algorithm;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KMPTest {
    private static final Logger logger = LoggerFactory.getLogger(KMPTest.class);

    @Test
    public void match() {
        try {
            KMP kmp = new KMP("hello world");

            Assert.assertTrue(kmp.match("ni hao hello world hah "));
            Assert.assertFalse(kmp.match("ni hao helo world hah "));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void firstMatch() {
        try {
            KMP kmp = new KMP("hello world");

            PatternHit hit = kmp.firstMatch("12345hello world678910");
            Assert.assertTrue(hit.equals(new PatternHit(5, 15, "hello world")));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void matchAll() {
        try {
            KMP kmp = new KMP("hhh");

            List<PatternHit> hits = kmp.matchAll("12345hhhhworld");

            Assert.assertTrue(hits.get(0).equals(new PatternHit(5, 7, "hhh")));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void matchAllNonCoincide() {
        try {
            KMP kmp = new KMP("hello world");

            List<PatternHit> hits = kmp.matchAllCoincide("12345hello world678910hello world");

            Assert.assertTrue(hits.get(0).equals(new PatternHit(5, 15, "hello world")));
            Assert.assertTrue(hits.get(1).equals(new PatternHit(22, 32, "hello world")));

            KMP kmp2 = new KMP("hhh");

            List<PatternHit> hits2 = kmp2.matchAllCoincide("12345hhhhworld");

            Assert.assertTrue(hits2.get(0).equals(new PatternHit(5, 7, "hhh")));
            Assert.assertTrue(hits2.get(1).equals(new PatternHit(6, 8, "hhh")));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}