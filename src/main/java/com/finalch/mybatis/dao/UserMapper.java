package com.finalch.mybatis.dao;

import com.finalch.mybatis.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
  public User getUserById(@Param("id") Integer id);

  public void updateUserName(@Param("id") Integer id, @Param("name") String name);

  public void updateUserAge(@Param("id") Integer id, @Param("age") Integer age);
}
