package me.dslztx.assist.frame;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class Booter {

  protected ClassPathXmlApplicationContext applicationContext;

  public Booter() {
    applicationContext = new ClassPathXmlApplicationContext(
        "booter.xml");
    start();
  }

  protected abstract void start();
}
