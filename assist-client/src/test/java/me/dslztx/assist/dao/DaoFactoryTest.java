package me.dslztx.assist.dao;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

import junit.framework.Assert;
import me.dslztx.assist.client.mysql.DaoFactory;
import me.dslztx.assist.client.mysql.DataSourceFactory;
import me.dslztx.assist.util.CloseableAssist;

public class DaoFactoryTest {

    private static final Logger logger = LoggerFactory.getLogger(DaoFactoryTest.class);

    @Test
    public void obtainDao() throws Exception {
        try {
            DataSource dataSource = new DruidDataSource();
            System.out.println(dataSource.hashCode());

            UserDao userDaoFirst = DaoFactory.obtainDao(dataSource, UserDao.class);

            UserDao userDaoSecond = DaoFactory.obtainDao(dataSource, UserDao.class);

            Assert.assertTrue(userDaoFirst == userDaoSecond);

            userDaoFirst.insertUser(new User());
            userDaoSecond.insertUser(new User());
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void test2() {
        try {
            DataSource dataSource = DataSourceFactory.obtainDataSource("in");

            TestDao dao = DaoFactory.obtainDao(dataSource, TestDao.class);

            Assert.assertTrue(dao.count() == 50);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void test3() {
        try {
            DataSource dataSource = DataSourceFactory.obtainDataSource("in");

            Test2Dao dao = DaoFactory.obtainDao(dataSource, Test2Dao.class);

            Assert.assertTrue(dao.selectCount() == 50);
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

    interface TestMapper extends Mapper {
        @Select("select count(*) from User")
        int count();
    }

    private static class UserDao extends Dao {

        public void insertUser(User user) {
            DataSource dataSource = obtainDataSource();
            // 使用dataSource把User插入到数据库

            System.out.println(dataSource.hashCode());
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

    public static class TestDao extends Dao {

        public int count() {
            SqlSession session = obtainSqlSessionFactory().openSession();
            try {
                TestMapper testMapper = session.getMapper(TestMapper.class);
                return testMapper.count();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                CloseableAssist.closeQuietly(session);
            }
            return -1;
        }
    }

    public static class Test2Dao extends Dao {

        public int selectCount() {
            SqlSession session = obtainSqlSessionFactory().openSession();
            try {
                Test2Mapper testMapper = session.getMapper(Test2Mapper.class);
                return testMapper.selectCount();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                CloseableAssist.closeQuietly(session);
            }
            return -1;
        }
    }
}