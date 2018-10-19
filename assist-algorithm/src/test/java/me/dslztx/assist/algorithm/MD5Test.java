package me.dslztx.assist.algorithm;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class MD5Test {

  @Test
  public void md5() throws Exception {
    try {
      assertTrue(MD5.md5("hello world").equals("5eb63bbbe01eeed093cb22bb8f5acdc3"));
      assertTrue(MD5.md5("great wall").equals("1bf62cc6bd5ae248f6ee26d395502ecc"));
    } catch (Exception e) {
      fail();
    }
  }

}