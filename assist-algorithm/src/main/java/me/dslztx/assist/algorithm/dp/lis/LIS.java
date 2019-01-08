package me.dslztx.assist.algorithm.dp.lis;

import me.dslztx.assist.util.ArrayAssist;

public class LIS {

    int[] sequence;

    int length;

    int[] tail;

    int lenOfLIS;

    int[] dp;

    public LIS(int[] sequence) {
        if (ArrayAssist.isEmpty(sequence)) {
            throw new RuntimeException("sequence is empty");
        }

        this.sequence = sequence;
        this.length = sequence.length;
    }

    public int lengthOfLIS() {
        dp = new int[length + 1];

        tail = new int[length];

        int len = 1;
        dp[len] = sequence[0];

        for (int index = 1; index < length; index++) {
            // 更新dp，看是否值需要更新成sequence[index]

            // 具体策略是：找到最大maxlen，使得dp[maxlen]<sequence[index]，则dp[maxlen+1]=sequence[index]
            int left = 1, right = len, mid;
            while (left <= right) {
                mid = (left + right) >> 1;

                if (dp[mid] < sequence[index]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            len = left;
            dp[len] = sequence[index];
            tail[index] = len;
        }

        lenOfLIS = len;

        return lenOfLIS;
    }

    public int numberOfLIS() {
        if (tail == null) {
            lengthOfLIS();
        }

        int[] number = new int[length];

        for (int index = 0; index < length; index++) {
            int num = 0;
            for (int preIndex = 0; preIndex < index; preIndex++) {
                if (sequence[preIndex] < sequence[index]) {
                    if (tail[preIndex] + 1 == tail[index]) {
                        num += number[preIndex];
                    }
                } else if (sequence[preIndex] == sequence[index]) {
                    if (tail[preIndex] == tail[index]) {
                        number[preIndex] = 0;
                    }
                }
            }

            if (num == 0) {
                num = 1;
            }

            number[index] = num;
        }

        int sum = 0;

        for (int index = 0; index < length; index++) {
            if (tail[index] == lenOfLIS) {
                sum += number[index];
            }
        }

        return sum;
    }

    public int[] obtainOneSolutionOfLIS() {
        int[] oneSolution = new int[lenOfLIS];
        for (int len = lenOfLIS; len >= 1; len--) {
            oneSolution[len - 1] = dp[len];
        }

        return oneSolution;
    }
}
