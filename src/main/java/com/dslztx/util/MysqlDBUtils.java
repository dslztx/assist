package com.dslztx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @date 2015年5月20日
 * @author dslztx
 * @version 2015.11.12
 */
public class MysqlDBUtils {
    private static final Logger logger = LoggerFactory.getLogger(MysqlDBUtils.class);

    /**
     * 数据库连接URL
     */
    String dbUrl;

    /**
     * SQL语句
     */
    String sql;

    /**
     * 数据库连接对象
     */
    private Connection connection;

    /**
     * 预编译执行语句对象
     */
    private PreparedStatement prepareStatement;

    /**
     * 静态初始化加载驱动器类
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 传入数据库连接地址和SQL语句
     * 
     * @param dbUrl
     * @param sql
     */
    public MysqlDBUtils(String dbUrl, String sql) {
        this.dbUrl = dbUrl;
        this.sql = sql;
    }

    /**
     * 打开数据库连接，建立预编译执行语句对象
     * 
     * @throws SQLException
     */
    public void open() throws SQLException {
        connection = DriverManager.getConnection(dbUrl);
        prepareStatement = connection.prepareStatement(sql);
    }

    /**
     * 得到内部生成的预编译执行语句对象，主要用于传入参数值
     * 
     * @return
     */
    public PreparedStatement getPreparedStatement() {
        return prepareStatement;
    }

    /**
     * 执行select命令
     * 
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery() throws SQLException {
        return prepareStatement.executeQuery();
    }

    /**
     * 执行insert,update,delete命令，返回受到影响的行数
     * 
     * @return
     * @throws SQLException
     */
    public int executeUpdate() throws SQLException {
        return prepareStatement.executeUpdate();
    }

    /**
     * 清理资源，注意一定要先关闭prepareStatement，再关闭connection，如果顺序搞错，会出现“Communications link failure”
     */
    public void close() {
        if (prepareStatement != null) {
            try {
                prepareStatement.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
}
