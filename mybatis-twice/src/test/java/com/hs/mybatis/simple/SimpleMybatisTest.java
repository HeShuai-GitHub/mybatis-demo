package com.hs.mybatis.simple;

import com.hs.mybatis.simple.mapper.UserMapper;
import com.hs.mybatis.simple.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.util.List;

/**
 * @author heshuai
 * @title: SimpleMybatisTest
 * @description: TODO
 * @date 2021年06月05日 20:09
 */
public class SimpleMybatisTest {
    /**
     * 使用动态代理方式去使用mybatis开发
     */
    @Test
    public void simpleDynamicTest() throws Exception {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("simple/SqlConfig.xml"));
        SqlSession sqlSession =factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAll();
        users.forEach(user -> System.out.println(user));
    }
    /**
     * 使用传统方式去使用mybatis开发
     */
    @Test
    public void simpleNonDynamicTest() throws Exception {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("simple/SqlConfig.xml"));
        SqlSession sqlSession =factory.openSession();
        List<User> users = sqlSession.selectList("com.hs.mybatis.simple.dao.UserMapper.findAll");
        users.forEach(user -> System.out.println(user));
    }

}
