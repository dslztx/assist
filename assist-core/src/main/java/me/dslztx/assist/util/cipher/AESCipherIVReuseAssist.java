package me.dslztx.assist.util.cipher;

import java.io.InputStream;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.ClassPathResourceAssist;

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
        InputStream in = null;

        try {
            in = ClassPathResourceAssist.locateInputStream("aes.cipher");

            if (in == null) {
                throw new RuntimeException("no aes key specified");
            }

            byte[] bb = IOUtils.toByteArray(in);

            if (bb == null || bb.length < 16) {
                throw new RuntimeException("the aes key is less than 16 bytes");
            }

            if (bb.length > 16) {
                log.warn("the aes key is greater than 16 bytes, truncate");

                bb = Arrays.copyOfRange(bb, 0, 16);
            }

            return new SecretKeySpec(bb, DEFAULT_ALGORITHM);
        } catch (Exception e) {
            log.error("", e);

            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ignore) {

                }
            }
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
