package me.dslztx.assist.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

/**
 * @author dslztx
 */
public class CharCodingAssist {

    /**
     * 以特定编码方案编码字符串，返回编码得到的字节流
     */
    public static byte[] encode(String str, Charset charset) {
        if (StringAssist.isBlank(str)) {
            return null;
        }

        if (charset == null) {
            throw new RuntimeException("charset is null");
        }

        return str.getBytes(charset);
    }

    /**
     * 以特定编码方案解码字节流，返回解码得到的字符串
     */
    public static String decode(byte[] bytes, Charset charset) {
        if (ArrayAssist.isEmpty(bytes)) {
            return null;
        }

        if (charset == null) {
            throw new RuntimeException("charset is null");
        }

        return new String(bytes, charset);
    }

    public static boolean isFileNotEncodedWith(File file, Charset charset) throws IOException {
        if (file == null || !file.exists()) {
            throw new RuntimeException("file not exists");
        }

        if (charset == null) {
            throw new RuntimeException("charset not specify");
        }

        byte[] direct = IOUtils.toByteArray(new FileInputStream(file));

        String ss = new String(direct, charset);

        byte[] indirect = ss.getBytes(charset);

        return !Arrays.equals(direct, indirect);
    }

    public static void main(String[] args) {
        String a = "�";
        String b =
            CharCodingAssist.decode(CharCodingAssist.encode("�", Charset.forName("GBK")), Charset.forName("GBK"));
        System.out.println(a.equals(b));
    }
}
