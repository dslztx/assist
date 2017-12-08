package me.dslztx.assist.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

  @Autowired
  UserMapper userMapper;

  User selectUser(Integer id) {
    return userMapper.selectUser(id);
  }
}
