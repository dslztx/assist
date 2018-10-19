package me.dslztx.assist.util;

import java.io.Serializable;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeAssistTest {

  private static final Logger logger = LoggerFactory.getLogger(SerializeAssistTest.class);

  @Test
  public void serializeToByteArrayTest() {
    try {
      Person p = new Person();
      p.setAge(10);
      p.setName("test");

      byte[] bb = SerializeAssist.serializeToByteArray(p);
      Assert.assertTrue(bb.length != 0);
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

  @Test
  public void deserializeFromByteArrayTest() {
    try {
      Person p = new Person();
      p.setAge(10);
      p.setName("test");

      byte[] bb = SerializeAssist.serializeToByteArray(p);

      Person p2 = SerializeAssist.deserializeFromByteArray(bb, Person.class);

      Assert.assertTrue(p.getName().equals(p2.getName()));
      Assert.assertTrue(p.getAge() == p2.getAge());
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }
}

class Person implements Serializable {

  private static final long serialVersionUID = 6810787772237440198L;

  String name;
  int age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}