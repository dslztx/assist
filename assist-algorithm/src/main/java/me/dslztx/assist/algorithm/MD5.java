package me.dslztx.assist.algorithm;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
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
        if (ArrayAssist.isEmpty(bb) || len <= 0)
            return null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();
            messageDigest.update(bb, 0, len);

            byte[] resultBytes = messageDigest.digest();
            return Hex.encodeHexString(resultBytes);
        } catch (Exception e) {
            return null;
        }
    }
}
