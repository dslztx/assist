package me.dslztx.assist.dao;

import javax.sql.DataSource;

/**
 * Dao类及其子类应用场景为：单实例，多线程。因此，须避免在方法中引入非线程安全语句
 */
public abstract class Dao {

  private DataSource dataSource;

  protected Dao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  protected DataSource getDataSource() {
    return dataSource;
  }

}