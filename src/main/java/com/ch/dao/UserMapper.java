package com.ch.dao;

import com.ch.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public User getUserById(@Param("id") Integer id);
}
