package me.dslztx.assist.algorithm;

import java.util.ArrayList;
import java.util.List;

// TODO
public class KMP {

    String pattern;

    int[] fail;

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

    // void GetNextval(char* p, int next[])
    // {
    // int pLen = strlen(p);
    // next[0] = -1;
    // int k = -1;
    // int j = 0;
    // while (j < pLen - 1)
    // {
    // //p[k]表示前缀，p[j]表示后缀
    // if (k == -1 || p[j] == p[k])
    // {
    // ++j;
    // ++k;
    // //较之前next数组求法，改动在下面4行
    // if (p[j] != p[k])
    // next[j] = k; //之前只有这一行
    // else
    // //因为不能出现p[j] = p[ next[j ]]，所以当出现时需要继续递归，k = next[k] = next[next[k]]
    // next[j] = next[k];
    // }
    // else
    // {
    // k = next[k];
    // }
    // }
    //
    // int len=-1;
    // for(int j=1;j<pLen-1;j++){
    // while(len>-1 && )
    // }
    // }

    public boolean match(String str) {
        int i = 0;
        int j = 0;
        while (i < str.length() && j < pattern.length()) {
            if (str.charAt(i) == str.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    return true;
                }
            } else {
                if (fail[j] == -1) {
                    i++;
                    j = 0;
                } else {
                    j = fail[j];
                }
            }
        }

        return false;
    }

    public int firstMatchIndex(String str) {
        int i = 0;
        int j = 0;
        while (i < str.length() && j < pattern.length()) {
            if (str.charAt(i) == str.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    return i - pattern.length();
                }
            } else {
                if (fail[j] == -1) {
                    i++;
                    j = 0;
                } else {
                    j = fail[j];
                }
            }
        }

        return -1;
    }

    public int[] wholeMatchIndexes(String str) {
        List<Integer> result = new ArrayList<Integer>();

        int i = 0;
        int j = 0;
        while (i < str.length() && j < pattern.length()) {
            if (str.charAt(i) == str.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    result.add(i - pattern.length());

                    j = 0;
                }
            } else {
                if (fail[j] == -1) {
                    i++;
                    j = 0;
                } else {
                    j = fail[j];
                }
            }
        }

        int[] result2 = new int[result.size()];
        for (int index = 0; index < result.size(); index++) {
            result2[index++] = result.get(index);
        }

        return result2;
    }

    public int[] wholeMatchIndexesOverride(String str) {
        List<Integer> result = new ArrayList<Integer>();

        int i = 0;
        int j = 0;
        while (i < str.length() && j < pattern.length()) {
            if (str.charAt(i) == str.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    result.add(i - pattern.length());

                    i = i - pattern.length() + 1;
                    j = 0;
                }
            } else {
                if (fail[j] == -1) {
                    i++;
                    j = 0;
                } else {
                    j = fail[j];
                }
            }
        }

        int[] result2 = new int[result.size()];
        for (int index = 0; index < result.size(); index++) {
            result2[index++] = result.get(index);
        }

        return result2;
    }

    // private void buildFailOptimize() {
    // next[0] = -1;
    // int j = 0;
    // int k = next[j];
    //
    // int len = -1;
    // for (int j = 1; j < pattern.length() - 1; j++) {
    // {
    //
    // while (len >= 0 && (pattern.charAt(j) != pattern.charAt(len))) {
    // len = next[k];
    // }
    //
    // if (len == -1) {
    // next[j + 1] = -1;
    // } else {
    // next[j + 1] = len + 1;
    // len = len + 1;
    // }
    // // p[k]表示前缀，p[j]表示后缀
    // if (k == -1 || pattern.charAt(j - 1) == pattern.charAt(k)) {
    // ++k;
    //
    // if (pattern.charAt(j) != pattern.charAt(k)) {
    // next[j] = k;
    // } else {
    // next[j] = next[k];
    // }
    //
    // j++;
    // } else {
    // k = next[k];
    // }
    // }
    // }
    // }

    /**
     * 分为3个步骤，可读性强，易理解
     */
    private void buildFail() {
        int[] longestCommon = new int[pattern.length()];

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

        for (int j = 0; j < pattern.length(); j++) {
            if (fail[j] != -1 && pattern.charAt(j) == pattern.charAt(fail[j])) {
                fail[j] = fail[fail[j]];
            }
        }

        return;
    }

}
