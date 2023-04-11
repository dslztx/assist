package me.dslztx.assist.util.cipher;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.ClassPathResourceAssist;

/**
 * 1. 对称加密算法 vs 非对称加密算法：安全性，前者劣于后者；计算速度，前者优于后者<br/>
 * 2. 在众多对称加密算法中，AES具有最好的“安全性”和“计算速度”权衡，应用最为广泛，得到CPU的硬件优化支持<br/>
 *
 * <br/>
 *
 * 参数选择：<br/>
 * 1. 基于速度考虑，选择AES-128，即密钥长度128位（16字节）<br/>
 * 2. 块加密子算法选择GCM，GCM为流式加密算法，此时无需进行字节填充<br/>
 * 3. 身份验证标记位长度选择建议的128位<br/>
 * 4. IV向量，设计用来防止同一块明文永远有相同的密文，建议12字节，须是随机数，可明文传递用于解密<br/>
 *
 * <br/>
 *
 * 参考文献：<br/>
 * [1]https://crypto.stackexchange.com/questions/26783/ciphertext-and-tag-size-and-iv-transmission-with-aes-in-gcm-mode<br/>
 * [2]https://crypto.stackexchange.com/questions/41601/aes-gcm-recommended-iv-size-why-12-bytes<br/>
 * [3]https://stackoverflow.com/questions/67028762/why-aes-256-with-gcm-adds-16-bytes-to-the-ciphertext-size<br/>
 * [4]https://crypto.stackexchange.com/questions/3499/why-cant-the-iv-be-predictable-when-its-said-it-doesnt-need-to
 * -be-a-secret<br/>
 * [5]https://crypto.stackexchange.com/questions/26790/how-bad-it-is-using-the-same-iv-twice-with-aes-gcm<br/>
 * [6]https://thiscute.world/posts/practical-cryptography-basics-6-symmetric-key-ciphers/#%E4%B8%89%E5%88%86%E7%BB%84
 * %E5%AF%86%E7%A0%81%E5%B7%A5%E4%BD%9C%E6%A8%A1%E5%BC%8F<br/>
 * [7]https://www.elttam.com/blog/key-recovery-attacks-on-gcm/
 */
@Slf4j
public class AESCipherAssist {

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

    private static final SecureRandom secureRandom = new SecureRandom();

    private static SecretKey generateSecretKey() {
        InputStream in = null;

        try {
            in = ClassPathResourceAssist.locateInputStream("aes.key");

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

    public static byte[] encrypt(byte[] plainData) {
        try {
            // 非线程安全，每次新建一个，可以用ThreadLocal进行一定优化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            byte[] iv = new byte[12];
            secureRandom.nextBytes(iv);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

            cipher.init(Cipher.ENCRYPT_MODE, KEY, spec);

            byte[] cipherData = cipher.doFinal(plainData);

            ByteBuffer byteBuffer = ByteBuffer.allocate(12 + cipherData.length);

            byteBuffer.put(iv);
            byteBuffer.put(cipherData);

            return byteBuffer.array();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(byte[] cipherData) {
        try {
            // 非线程安全，每次新建一个，可以用ThreadLocal进行一定优化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            // 明文传递过来的IV向量
            byte[] iv = Arrays.copyOfRange(cipherData, 0, 12);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

            cipher.init(Cipher.DECRYPT_MODE, KEY, spec);

            return cipher.doFinal(cipherData, 12, cipherData.length - 12);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
