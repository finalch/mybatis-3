package com.finalch.mybatis;

import com.finalch.mybatis.po.User;
import org.apache.ibatis.executor.Executor;
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
import java.util.List;

/**
 * @author: cjianping on 2021/2/22 9:59
 */
public class TestExecutor {
  Configuration configuration;
  Transaction transaction;

  @BeforeEach
  public void init() throws IOException, SQLException {
    InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
    // 实例化sqlSessionFactory   ----  DefaultSqlSessionFactory
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    configuration = sqlSessionFactory.getConfiguration();
    transaction = new JdbcTransaction(configuration.getEnvironment().getDataSource().getConnection());
  }

  @Test
  public void testSimpleExecutor() throws SQLException {
    Executor executor = new SimpleExecutor(configuration, transaction);
    MappedStatement mappedStatement = configuration.getMappedStatement("com.finalch.mybatis.dao.UserMapper.getUserById");
    RowBounds rowBounds = new RowBounds();
    List<User> query = executor.query(mappedStatement, 1, rowBounds, null);
    System.out.println(query.get(0).getName());
  }
}
