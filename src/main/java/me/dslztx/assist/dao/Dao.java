package me.dslztx.assist.dao;

import javax.sql.DataSource;

public abstract class Dao {

  private DataSource dataSource;

  protected Dao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  protected DataSource getDataSource() {
    return dataSource;
  }

}