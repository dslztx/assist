package me.dslztx.assist.dao;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;

import me.dslztx.assist.util.ObjectAssist;

/**
 * Dao类及其子类应用场景为：单实例，多线程。因此，须避免在方法中引入非线程安全语句<br/>
 * not thread safe
 */
public abstract class Dao {

    private DataSource dataSource;

    private SqlSessionFactory sqlSessionFactory;

    public DataSource obtainDataSource() {
        if (ObjectAssist.isNull(dataSource)) {
            throw new RuntimeException("no datasource");
        }

        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SqlSessionFactory obtainSqlSessionFactory() {
        if (ObjectAssist.isNull(sqlSessionFactory)) {
            throw new RuntimeException("no sqlSessionFactory");
        }

        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}