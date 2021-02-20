package com.ch;

import com.ch.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: cjianping on 2021/2/4 11:50
 */
public class M {
  public static void main(String[] args) {
    InputStream inputStream = null;
    try {
      // 从resources目录下加载配置文件
      inputStream = Resources.getResourceAsStream("mybatis-config.xml");
      // 实例化sqlSessionFactory   ----  DefaultSqlSessionFactory
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.REUSE)) {
        // 执行sql方法
        User user = (User) session.selectOne("com.ch.dao.UserMapper.getUserById", 1);
        System.out.println(user.getName());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
