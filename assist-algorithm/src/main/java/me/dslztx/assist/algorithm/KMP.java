package me.dslztx.assist.algorithm;

import java.util.ArrayList;
import java.util.List;

import me.dslztx.assist.util.StringAssist;

/**
 * <p>
 * KMP算法：相较于朴素匹配，根据模式串的自身特点，构造一个fail数组，表征对于主串的p位，如果模式串的q位匹配失败，应该再尝试模式串的fail[q]位
 * </p>
 * 
 * <p>
 * 参考1中构造next数组（即这里的fail数组）时，几个步骤合并在一起，虽然性能有点提升，代码也更加简练，但是可读性差。这里是作者参考自己实现的，buildFail()是2个步骤，buildFailOptimize()是3个步骤
 * </p>
 * 
 * <br/>
 * 参考：<br/>
 * 1）https://blog.csdn.net/v_july_v/article/details/7041827<br/>
 * 2）http://www.ruanyifeng.com/blog/2013/05/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm.html <br/>
 * 
 */
public class KMP {

    String pattern;

    int[] fail;

    public KMP(String pattern) {
        if (StringAssist.isBlank(pattern)) {
            throw new RuntimeException("pattern can not be blank");
        }

        this.pattern = pattern;

        // buildFail();
        buildFailOptimize();
    }

    public boolean match(String str) {
        if (StringAssist.isBlank(str)) {
            return false;
        }

        int i = 0;
        int j = 0;
        while (i < str.length() && j < pattern.length()) {
            if (str.charAt(i) == pattern.charAt(j)) {
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

    public PattternHit firstMatch(String str) {
        if (StringAssist.isBlank(str)) {
            return null;
        }

        int i = 0;
        int j = 0;
        while (i < str.length() && j < pattern.length()) {
            if (str.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    return new PattternHit(i - pattern.length(), i - 1, pattern);
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

        return null;
    }

    public List<PattternHit> matchAll(String str) {
        List<PattternHit> result = new ArrayList<PattternHit>();

        if (StringAssist.isBlank(str)) {
            return result;
        }

        int i = 0;
        int j = 0;
        while (i < str.length() && j < pattern.length()) {
            if (str.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    result.add(new PattternHit(i - pattern.length(), i - 1, pattern));

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

        return result;
    }

    /**
     * 允许重合匹配
     */
    public List<PattternHit> matchAllCoincide(String str) {
        List<PattternHit> result = new ArrayList<PattternHit>();

        if (StringAssist.isBlank(str)) {
            return result;
        }

        int i = 0;
        int j = 0;
        while (i < str.length() && j < pattern.length()) {
            if (str.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    result.add(new PattternHit(i - pattern.length(), i - 1, pattern));

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

        return result;
    }

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

        optimizeFailTable(fail);

        return;
    }

    /**
     * 简化为两个步骤，可读性较强，较易理解<br/>
     * 基于buildFail()
     */
    private void buildFailOptimize() {
        fail = new int[pattern.length()];

        fail[0] = -1;

        int len;

        // 构造下一个
        for (int j = 0; j < pattern.length() - 1; j++) {
            len = fail[j];

            while (len != -1 && (pattern.charAt(len) != pattern.charAt(j))) {
                len = fail[len];
            }

            fail[j + 1] = len + 1;
        }

        optimizeFailTable(fail);
    }

    private void optimizeFailTable(int[] fail) {
        for (int j = 0; j < fail.length; j++) {
            if (fail[j] == -1) {
                continue;
            }

            if (pattern.charAt(j) == pattern.charAt(fail[j])) {
                fail[j] = fail[fail[j]];
            }
        }
    }
}
