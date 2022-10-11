package me.dslztx.assist.algorithm;

import org.apache.commons.codec.binary.Base64;

import me.dslztx.assist.util.ArrayAssist;
import me.dslztx.assist.util.StringAssist;

public class Base64Assist {

    public static String encodeBase64(byte[] bb) {
        if (ArrayAssist.isEmpty(bb)) {
            return null;
        }

        return Base64.encodeBase64String(bb);
    }

    public static byte[] decodeBase64(String s) {
        if (StringAssist.isBlank(s)) {
            return null;
        }

        return Base64.decodeBase64(s);
    }

}
