package me.dslztx.assist.dao;

import com.alibaba.druid.pool.DruidDataSource;
import junit.framework.Assert;
import me.dslztx.assist.client.mysql.DaoFactory;
import me.dslztx.assist.client.mysql.DataSourceFactory;
import me.dslztx.assist.util.CloseableAssist;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoFactoryTest {

    private static final Logger logger = LoggerFactory.getLogger(DaoFactoryTest.class);


    public void initDBData() throws SQLException {
        DataSource dataSource = DataSourceFactory.obtainDataSource("in");

        Connection connection = dataSource.getConnection();


        Statement statement = connection.createStatement();
        statement.execute("delete from `User`");

        String sql = "insert into `User`(`id`,`name`) values(1,'dslztx');";

        statement.executeUpdate(sql);
    }

    @Test
    public void obtainTheSameDao() {
        try {
            DataSource dataSource = new DruidDataSource();

            UserDao userDaoFirst = DaoFactory.obtainDao(dataSource, UserDao.class);

            UserDao userDaoSecond = DaoFactory.obtainDao(dataSource, UserDao.class);

            Assert.assertTrue(userDaoFirst == userDaoSecond);

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }


    @Test
    public void test0() {
        try {
            initDBData();

            DataSource dataSource = DataSourceFactory.obtainDataSource("in");

            UserDao dao = DaoFactory.obtainDao(dataSource, UserDao.class);

            Assert.assertTrue(dao.count() == 1);

            Assert.assertTrue(dao.listUsers().size() == 1);

            User user = dao.listUsers().get(0);
            Assert.assertTrue(user.getName().equals("dslztx"));
            Assert.assertTrue(user.getId() == 1);

        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }


    static interface UserMapper extends Mapper {

        @Select("select * from User")
        List<User> listUsers();

        @Select("select count(*) from User")
        int count();
    }

    static class UserDao extends Dao {

        public List<User> listUsers() {

            SqlSession session = obtainSqlSessionFactory().openSession();
            try {
                UserMapper userMapper = session.getMapper(UserMapper.class);
                return userMapper.listUsers();
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                CloseableAssist.closeQuietly(session);
            }

            return new ArrayList<User>();
        }

        public int count() {
            SqlSession session = obtainSqlSessionFactory().openSession();
            try {
                UserMapper userMapper = session.getMapper(UserMapper.class);
                return userMapper.count();
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                CloseableAssist.closeQuietly(session);
            }
            return -1;
        }
    }

    static class User {

        Long id;

        String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}