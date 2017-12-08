package me.dslztx.assist.mybatis;

import me.dslztx.assist.frame.Booter;

public class MybatisTest {

  public static void main(String[] args) {
    MyBooter myBooter = new MyBooter();
  }
}

class MyBooter extends Booter {

  @Override
  protected void start() {
    UserDao userDao = (UserDao) applicationContext.getBean("userDao");
    System.out.println(userDao.selectUser(5211));
  }
}
