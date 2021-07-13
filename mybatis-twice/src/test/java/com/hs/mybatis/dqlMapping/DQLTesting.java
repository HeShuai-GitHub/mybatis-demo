package com.hs.mybatis.dqlMapping;

import com.hs.mybatis.dqlMapping.mapper.OrderMapper;
import com.hs.mybatis.dqlMapping.mapper.UserMapper;
import com.hs.mybatis.dqlMapping.pojo.Order;
import com.hs.mybatis.dqlMapping.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.lang.invoke.VarHandle;
import java.util.List;

/**
 * @author heshuai
 * @title: DQLTesting
 * @description: TODO
 * @date 2021年06月05日 21:19
 */
public class DQLTesting {
    /**
     * 一对一映射
     */
    @Test
    public void OneToOne() throws Exception {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("dqlMapping/SqlConfig.xml"));
        SqlSession sqlSession =factory.openSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        Order order = mapper.selectOrderById(1);
        System.out.println(order);
        System.out.println("**************************");
        List<Order> orders = mapper.selectOrderByCondition(new Order(null, null, null, 1, null));
        orders.forEach((order1)->{
            System.out.println(order1);
        });
    }

    /**
     * 一对多映射
     */
    @Test
    public void oneToMany() throws Exception{
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("dqlMapping/SqlConfig.xml"));
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectUserByCondition(new User(1, null, null, null, null,null));
        users.forEach(user -> System.out.println(user));
    }

    /**
     * 多对多映射
     */
    @Test
    public void manyToMany() throws Exception{
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("dqlMapping/SqlConfig.xml"));
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectUserAndRoleALl();
        users.forEach(user -> System.out.println(user));
    }
}
