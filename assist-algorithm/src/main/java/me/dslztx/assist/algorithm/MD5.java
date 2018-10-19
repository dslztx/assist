package me.dslztx.assist.algorithm;

import org.apache.commons.codec.digest.DigestUtils;

import me.dslztx.assist.util.ArrayAssist;

/**
 * @author dslztx
 */
public class MD5 {

    /**
     * MD5算法
     *
     * @return 32位十六进制小写字符
     */
    public static String md5(String s) {
        if (s == null) {
            return null;
        }

        return DigestUtils.md5Hex(s);
    }

    public static String md5(byte[] bb) {
        if (ArrayAssist.isEmpty(bb)) {
            return null;
        }

        return DigestUtils.md5Hex(bb);
    }

    public static String md5(byte[] bb, int len) {
        if (ArrayAssist.isEmpty(bb)) {
            return null;
        }

        byte[] dst = new byte[len];

        System.arraycopy(bb, 0, dst, 0, len);

        return DigestUtils.md5Hex(dst);
    }
}
