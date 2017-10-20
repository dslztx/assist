package me.dslztx.assist.algorithm;

import org.apache.commons.codec.digest.DigestUtils;

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
    return DigestUtils.md5Hex(s);
  }
}
