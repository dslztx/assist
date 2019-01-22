package me.dslztx.assist.algorithm.dp.lcs;

/**
 * 可进行滚动数组优化，空间复杂度降低，代价是可读性变差
 */
public class LCS {

    int[] sa;
    int[] sb;

    int[][] dp;
    int lenOfLCS = -1;

    public LCS(int[] sa, int[] sb) {
        this.sa = sa;
        this.sb = sb;
    }

    public int lengthOfLCS() {
        dp = new int[sa.length + 1][sb.length + 1];

        dp[0][0] = 0;

        for (int indexA = 1; indexA <= sa.length; indexA++) {
            dp[indexA][0] = 0;
        }

        for (int indexB = 1; indexB <= sb.length; indexB++) {
            dp[0][indexB] = 0;
        }

        for (int indexA = 1; indexA <= sa.length; indexA++) {
            for (int indexB = 1; indexB <= sb.length; indexB++) {
                if (sa[indexA - 1] == sb[indexB - 1]) {
                    dp[indexA][indexB] = dp[indexA - 1][indexB - 1] + 1;
                } else {
                    dp[indexA][indexB] = Math.max(dp[indexA - 1][indexB], dp[indexA][indexB - 1]);
                }
            }
        }

        lenOfLCS = dp[sa.length][sb.length];

        return lenOfLCS;
    }

    public int[] obtainOneSolutionOfLCS() {
        if (lenOfLCS == -1) {
            lengthOfLCS();
        }

        int length = lenOfLCS;

        int[] result = new int[length];

        int indexA = sa.length - 1;
        int indexB = sb.length - 1;

        while (length > 0) {
            if (sa[indexA] == sb[indexB]) {
                result[length - 1] = sa[indexA];
                length--;
                indexA--;
                indexB--;
            } else {
                if (dp[indexA + 1][indexB + 1] == dp[indexA][indexB]) {
                    indexA--;
                    indexB--;
                } else {
                    if (dp[indexA + 1][indexB] > dp[indexA][indexB + 1]) {
                        indexB--;
                    } else {
                        indexA--;
                    }
                }
            }
        }

        return result;
    }
}
