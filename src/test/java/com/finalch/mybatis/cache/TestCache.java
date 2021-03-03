package com.finalch.mybatis.cache;

import com.finalch.mybatis.dao.UserMapper;
import com.finalch.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * @author: cjianping on 2021/2/23 15:43
 */
public class TestCache {
  Configuration configuration;
  Transaction transaction;
  SqlSessionFactory sqlSessionFactory;

  @BeforeEach
  public void init() throws IOException, SQLException {
    InputStream inputStream = Resources.getResourceAsStream("com/finalch/mybatis/cache/mybatis-config.xml");
    // 实例化sqlSessionFactory   ----  DefaultSqlSessionFactory
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    configuration = sqlSessionFactory.getConfiguration();
    transaction = new JdbcTransaction(configuration.getEnvironment().getDataSource().getConnection());
  }

  /**
   * 同一个session
   * 同一个statement
   * 同样的查询条件
   * 同样的分页条件
   * 同样的sql
   */
  @Test
  public void localCacheTest() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    User user = userMapper.getUserById(1);
    System.out.println(user == null ? "null" : user.getName());
    User user1 = userMapper.getUserById(1);
    System.out.println(user1 == null ? "null" : user1.getName());
  }

  @Test
  public void localCacheTest2() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    User user = userMapper.getUserById(1);
    System.out.println(user == null ? "null" : user.getName());
    User user1 = userMapper.getUserById(2);
    System.out.println(user1 == null ? "null" : user1.getName());
  }
}
