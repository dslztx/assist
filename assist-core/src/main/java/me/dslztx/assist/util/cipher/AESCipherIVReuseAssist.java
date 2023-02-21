package me.dslztx.assist.util.cipher;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import lombok.extern.slf4j.Slf4j;

/**
 * 详细介绍可参见AESCipherAssist，本版本复用IV向量，实现相对简单，代价是安全性降低
 */
@Slf4j
public class AESCipherIVReuseAssist {

    /**
     * AES算法
     */
    private static final String DEFAULT_ALGORITHM = "AES";

    /**
     * AES算法，GCM块加密算法，无需进行字节填充
     *
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/GCM/NOPADDING";

    /**
     * 随机生成16字节，并进行Hex编码
     */
    private static final String key = "360f5c33457a11321c40707d115d6200";

    private static final SecretKey KEY = generateSecretKey();

    /**
     * 身份验证标记位长度128位
     */
    private static final int GCM_TAG_LENGTH = 16 * 8;

    /**
     * IV向量，固定12字节，复用
     */
    private static final byte[] IV = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b};

    private static final GCMParameterSpec GCM_PARAMETER = generateGCMParameter();

    private static SecretKey generateSecretKey() {
        try {
            byte[] bb = Hex.decodeHex(key.toCharArray());

            if (bb.length != 16) {
                throw new RuntimeException("the key length is illegal");
            }

            return new SecretKeySpec(bb, DEFAULT_ALGORITHM);
        } catch (Exception e) {

            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    private static GCMParameterSpec generateGCMParameter() {
        try {
            return new GCMParameterSpec(GCM_TAG_LENGTH, IV);
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(byte[] dd) {
        try {
            // 非线程安全，每次新建一个，可以用ThreadLocal进行一定优化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, KEY, GCM_PARAMETER);

            return cipher.doFinal(dd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(byte[] dd) {
        try {
            // 非线程安全，每次新建一个，可以用ThreadLocal进行一定优化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, KEY, GCM_PARAMETER);

            return cipher.doFinal(dd);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
