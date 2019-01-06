package me.dslztx.assist.algorithm.dp.lis;

public class LIS {

    int[] sequence;

    int[] tail;

    public LIS(int[] sequence) {
        this.sequence = sequence;
    }

    public int lengthOfLIS() {
        int[] dp = new int[sequence.length + 1];

        dp[1] = sequence[0];

        int len = 1;

        for (int pos = 1; pos < sequence.length; pos++) {
            if (dp[len] < sequence[pos]) {
                dp[++len] = sequence[pos];
            } else {
                int left = 1, right = len, mid;
                while (left <= right) {
                    mid = (left + right) >> 1;

                    if (dp[mid] < sequence[pos]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }

                dp[left] = sequence[pos];
            }
        }

        return len;
    }

//    public int solutionsOfLIS() {
//        if (tail == null) {
//            int len = lengthOfLIS();
//        }
//
//        int[] number = new int[sequence.length];
//
//        for (int i = 0; i < sequence.length; i++) {
//            int num = 0;
//            for (int j = 0; j < i; j++) {
//                if (sequence[j] < sequence[i]) {
//                    if (tail[j] + 1 == tail[i]) {
//                        num += number[j];
//                    }
//                }
//            }
//            if (num == 0) {
//                num = 1;
//            }
//
//            number[i] = num;
//        }
//    }
}
