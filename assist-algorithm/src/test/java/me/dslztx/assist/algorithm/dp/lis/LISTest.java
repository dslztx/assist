package me.dslztx.assist.algorithm.dp.lis;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class LISTest {

    @Test
    public void test() {
        try {
            LIS lis = new LIS(new int[] {3, 4, 3, 4, 5});
            Assert.assertTrue(lis.numberOfLIS() == 1);
            Assert.assertTrue(Arrays.equals(lis.obtainOneSolutionOfLIS(), new int[] {3, 4, 5}));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void test2() {
        try {
            LIS lis = new LIS(new int[] {1, 3, 4, 12, 2, 24, 54, 1, 23, 4, 100, 1000, 5, 6, 7, 8, 9, 10, 10000});
            Assert.assertTrue(lis.lengthOfLIS() == 10);
            Assert.assertNotNull(lis.numberOfLIS() == 2);

            Assert
                .assertTrue(Arrays.equals(lis.obtainOneSolutionOfLIS(), new int[] {1, 2, 4, 5, 6, 7, 8, 9, 10, 10000}));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}