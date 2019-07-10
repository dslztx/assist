package me.dslztx.assist.util;

import java.util.Random;

public class RandomAssist {

    /**
     *
     * @param lowerBound 包括在内
     * @param upperBound 不包括在内
     */
    public static int randomInt(int lowerBound, int upperBound) {
        if (upperBound <= lowerBound) {
            throw new RuntimeException("upperBound must be greater than lowerBound");
        }

        Random random = new Random();

        int diff = upperBound - lowerBound;

        return random.nextInt(diff) + lowerBound;
    }
}
