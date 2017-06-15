package com.dslztx.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class MysqlLog {
    String sql = "update activity set origin='hangzhou' where id=?";

    private void simple() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String mysqlUrl =
                "jdbc:mysql://127.0.0.1:3306/dslztx?useUnicode=true&characterEncoding=gbk&user=dsl&password=dsl";
        Connection connection = DriverManager.getConnection(mysqlUrl);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 1);
        statement.executeUpdate();
    }

    private void log4jdbc() throws Exception {
        Class.forName("net.sf.log4jdbc.DriverSpy");
        String mysqlUrl =
                "jdbc:log4jdbc:mysql://127.0.0.1:3306/dslztx?useUnicode=true&characterEncoding=gbk&user=dsl&password=dsl";
        Connection connection = DriverManager.getConnection(mysqlUrl);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 1);
        statement.executeUpdate();
    }

    private void springJDBCTemplate() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(sql, 1);
    }

    private void mybatis() throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/mybatis-config.xml")));
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, "mybatisDB");
        reader.close();
        SqlSession sqlSession = factory.openSession();
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("id", 1);
        sqlSession.update("Activity.updateActivity", map);
    }

    public static void main(String[] args) throws Exception {
        MysqlLog mysqlLog = new MysqlLog();
        // mysqlLog.simple();
        // mysqlLog.log4jdbc();
        // mysqlLog.springJDBCTemplate();
        mysqlLog.mybatis();
    }
}
