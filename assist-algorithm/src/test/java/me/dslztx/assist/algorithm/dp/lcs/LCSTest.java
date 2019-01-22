package me.dslztx.assist.algorithm.dp.lcs;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LCSTest {

    private static final Logger logger = LoggerFactory.getLogger(LCSTest.class);

    @Test
    public void test() {
        try {
            int[] sa = new int[] {1, 2, 3, 5, 5, 7, 9, 6};
            int[] sb = new int[] {1, 2, 3, 5, 6};

            LCS lcs = new LCS(sa, sb);
            Assert.assertTrue(lcs.lengthOfLCS() == 5);

            Assert.assertTrue(Arrays.equals(lcs.obtainOneSolutionOfLCS(), new int[] {1, 2, 3, 5, 6}));

            int[] saa = new int[] {1, 2, 3, 3, 5, 10, 10};
            int[] sbb = new int[] {10, 1, 10, 2, 3, 5, 5};

            LCS lcs2 = new LCS(saa, sbb);
            Assert.assertTrue(lcs2.lengthOfLCS() == 4);
            Assert.assertTrue(Arrays.equals(lcs2.obtainOneSolutionOfLCS(), new int[] {1, 2, 3, 5}));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}