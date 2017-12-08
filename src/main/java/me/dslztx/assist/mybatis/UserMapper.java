package me.dslztx.assist.mybatis;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

  User selectUser(Integer id);

}
