package me.dslztx.assist.algorithm;

// TODO
public class KMP {

    String pattern;

    int[] fail;

    int[] longestCommon;

    public KMP(String pattern) {
        this.pattern = pattern;

        buildFail();

        // buildFailOptimize();
    }

    public static void main(String[] args) {
        // KMP kmp = new KMP();
        // kmp.t = "ABCDABD".toCharArray();
        //
        // kmp.lent = 7;
        // kmp.nextval = new int[8];
        //
        // System.out.println("hello world");
    }

    public boolean match(String str) {
        return false;
    }

    public int firstMatchIndex(String str) {
        return -1;
    }

    public int[] wholeMatchIndexes(String str) {
        return null;
    }

    private void buildFailOptimize() {}

    private void buildFail() {
        longestCommon = new int[pattern.length()];

        longestCommon[0] = 0;

        int len;
        for (int i = 1; i < pattern.length(); i++) {

            len = longestCommon[i - 1];
            while (len > 0 && pattern.charAt(len) != pattern.charAt(i)) {
                len = longestCommon[len - 1];
            }

            if (pattern.charAt(len) != pattern.charAt(i)) {
                longestCommon[i] = 0;
            } else {
                longestCommon[i] = len + 1;
            }
        }

        fail = new int[pattern.length()];

        for (int i = pattern.length() - 1; i >= 1; i--) {
            fail[i] = longestCommon[i - 1];
        }

        fail[0] = -1;
    }

}
