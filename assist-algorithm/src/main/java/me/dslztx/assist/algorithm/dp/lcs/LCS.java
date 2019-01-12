package me.dslztx.assist.algorithm.dp.lcs;

/**
 * 可进行滚动数组优化，空间复杂度降低，代价是可读性变差
 */
// todo
public class LCS {

    int[] sa;
    int[] sb;

    int[][] dp;
    int lenOfLCS;

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
        int[] result = new int[lenOfLCS];
        int length = lenOfLCS;
        int i = sa.length;
        int j = sb.length;
        while (length > 0) {
            if (dp[i][j] == dp[i - 1][j - 1]) {
                i--;
                j--;
            } else {
                if (dp[i][j - 1] == dp[i - 1][j]) {
                    result[length - 1] = sa[i - 1];
                } else {
                    if (dp[i][j - 1] > dp[i - 1][j]) {
                        j--;
                    } else {
                        i--;
                    }
                }
            }
        }

        return result;
    }
}
