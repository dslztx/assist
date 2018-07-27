package me.dslztx.assist.util;

import java.nio.charset.Charset;

/**
 * @author dslztx
 */
public class CodingAssist {

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

  public static void main(String[] args) {
    String a = "�";
    String b = CodingAssist
        .decode(CodingAssist.encode("�", Charset.forName("GBK")), Charset.forName("GBK"));
    System.out.println(a.equals(b));
  }
}
