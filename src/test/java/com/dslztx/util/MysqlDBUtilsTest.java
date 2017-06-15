package com.dslztx.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * @author dslztx
 * @date 2015年11月12日
 */
public class MysqlDBUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(MysqlDBUtilsTest.class);

    public static void insertData() throws SQLException {
        String url =
                "jdbc:mysql://localhost:3306/dslztx?user=dsl&password=dsl&amp;useUnicode=true&amp;zeroDateTimeBehavior=convertToNull&amp;characterEncoding=GBK";
        String sql =
                "INSERT INTO `user` VALUES (1,'hi','男','hi@gmail.com'),(2,'hello','女','hello@gmail.com'),(3,'world','男','world@gmail.com');\n";

        MysqlDBUtils mysqlDBUtils = new MysqlDBUtils(url, sql);
        mysqlDBUtils.open();
        mysqlDBUtils.executeUpdate();
        mysqlDBUtils.close();
    }

    public static void clearData() throws SQLException {
        String url =
                "jdbc:mysql://localhost:3306/dslztx?user=dsl&password=dsl&amp;useUnicode=true&amp;zeroDateTimeBehavior=convertToNull&amp;characterEncoding=GBK";
        String sql = "delete from user";
        MysqlDBUtils mysqlDBUtils = new MysqlDBUtils(url, sql);
        mysqlDBUtils.open();
        mysqlDBUtils.executeUpdate();
        mysqlDBUtils.close();
    }


    @Test
    public void testExecuteQuery() throws SQLException {
        insertData();

        String url =
                "jdbc:mysql://localhost:3306/dslztx?user=dsl&password=dsl&amp;useUnicode=true&amp;zeroDateTimeBehavior=convertToNull&amp;characterEncoding=GBK";

        String sql = "select * from user where id>?";
        MysqlDBUtils mysqlDBUtils = new MysqlDBUtils(url, sql);
        mysqlDBUtils.open();
        PreparedStatement statement = mysqlDBUtils.getPreparedStatement();
        statement.setLong(1, 0);
        try {
            ResultSet resultSet = mysqlDBUtils.executeQuery();

            Long id;
            String name;
            String sex;
            String email;
            int line = 0;
            while (resultSet.next()) {
                line++;

                id = resultSet.getLong("id");
                name = resultSet.getString("name");
                sex = resultSet.getString("sex");
                email = resultSet.getString("email");
                if (line == 1) {
                    assertTrue(id.equals(1L));
                    assertTrue(name.equals("hi"));
                    assertTrue(sex.equals("男"));
                    assertTrue(email.equals("hi@gmail.com"));
                } else if (line == 2) {
                    assertTrue(id.equals(2L));
                    assertTrue(name.equals("hello"));
                    assertTrue(sex.equals("女"));
                    assertTrue(email.equals("hello@gmail.com"));
                } else {
                    assertTrue(id.equals(3L));
                    assertTrue(name.equals("world"));
                    assertTrue(sex.equals("男"));
                    assertTrue(email.equals("world@gmail.com"));
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            fail();
        } finally {
            mysqlDBUtils.close();
            clearData();
        }
    }
}
