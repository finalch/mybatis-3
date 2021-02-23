package com.finalch.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;

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
    InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
    // 实例化sqlSessionFactory   ----  DefaultSqlSessionFactory
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    configuration = sqlSessionFactory.getConfiguration();
    transaction = new JdbcTransaction(configuration.getEnvironment().getDataSource().getConnection());
  }
}
