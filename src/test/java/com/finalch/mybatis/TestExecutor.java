package com.finalch.mybatis;

import com.finalch.mybatis.po.User;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: cjianping on 2021/2/22 9:59
 */
public class TestExecutor {
  Configuration configuration;
  Transaction transaction;
  SqlSessionFactory sqlSessionFactory;

  @BeforeEach
  public void init() throws IOException, SQLException {
    InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
    // 实例化sqlSessionFactory   ----  DefaultSqlSessionFactory
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    configuration = sqlSessionFactory.getConfiguration();
    transaction = new JdbcTransaction(configuration.getEnvironment().getDataSource().getConnection());
  }

  @Test
  public void testSimpleExecutor() throws SQLException {
    Executor executor = new SimpleExecutor(configuration, transaction);
    MappedStatement mappedStatement = configuration.getMappedStatement("com.finalch.mybatis.dao.UserMapper.getUserById");
    RowBounds rowBounds = new RowBounds();
    List<User> query = executor.query(mappedStatement, 1, rowBounds, null);
    List<User> query1 = executor.query(mappedStatement, 2, rowBounds, null);
    System.out.println(query.get(0).getName());
    System.out.println(query1.get(0).getName());
  }

  @Test
  public void testReuseExecutor() throws SQLException {
    Executor executor = new ReuseExecutor(configuration, transaction);
    MappedStatement mappedStatement = configuration.getMappedStatement("com.finalch.mybatis.dao.UserMapper.getUserById");
    RowBounds rowBounds = new RowBounds();
    List<User> query = executor.query(mappedStatement, 1, rowBounds, null);
    List<User> query1 = executor.query(mappedStatement, 2, rowBounds, null);
    System.out.println(query.get(0).getName());
    System.out.println(query1.get(0).getName());
  }

  @Test
  public void testBatchExecutor() throws SQLException {
    Executor executor = new BatchExecutor(configuration, transaction);
    MappedStatement mappedStatement = configuration.getMappedStatement("com.finalch.mybatis.dao.UserMapper.updateUserName");
    RowBounds rowBounds = new RowBounds();
    Map<String, Object> params = new HashMap<>();
    params.put("id", 1);
    params.put("name", "王五");
    executor.update(mappedStatement, params);
    params = new HashMap<>();
    params.put("id", 2);
    params.put("name", "赵三");
    executor.update(mappedStatement, params);
    params = new HashMap<>();
    params.put("id", 2);
    params.put("age", 20);
    mappedStatement = configuration.getMappedStatement("com.finalch.mybatis.dao.UserMapper.updateUserAge");
    executor.update(mappedStatement, params);
    executor.commit(true);
  }
}
