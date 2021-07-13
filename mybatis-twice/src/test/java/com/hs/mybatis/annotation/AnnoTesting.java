package com.hs.mybatis.annotation;

import com.hs.mybatis.annotation.mapper.OrderMapper;
import com.hs.mybatis.annotation.mapper.RoleMapper;
import com.hs.mybatis.annotation.mapper.UserMapper;
import com.hs.mybatis.annotation.pojo.Order;
import com.hs.mybatis.annotation.pojo.Role;
import com.hs.mybatis.annotation.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author heshuai
 * @title: AnnoTesting
 * @description: TODO
 * @date 2021年06月05日 23:11
 */
public class AnnoTesting {

    public static SqlSession getSession() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("annotation/SqlConfig.xml"));
        SqlSession sqlSession = factory.openSession(true);  // 默认是不主动提交事务呢，所以需要设置为true，让他在每次修改数据后主动提交事务
        return sqlSession;
    }

    @Test
    public void simpleQuery() throws Exception{
        RoleMapper roleMapper = AnnoTesting.getSession().getMapper(RoleMapper.class);
        Role oneById = roleMapper.findOneById(1);
        System.out.println(oneById);
    }

    @Test
    public void simpleInsert() throws Exception{
        RoleMapper roleMapper = AnnoTesting.getSession().getMapper(RoleMapper.class);
        Role role = new Role();
        role.setId(10);
        role.setRoleName("ACE");
        role.setRoleDesc("ACE");
        roleMapper.insert(role);
    }

    @Test
    public void simpleUpdate() throws Exception{
        RoleMapper roleMapper = AnnoTesting.getSession().getMapper(RoleMapper.class);
        Role role = new Role();
        role.setId(10);
        role.setRoleName("ACE2");
        role.setRoleDesc("ACE2");
        roleMapper.update(role);
    }

    @Test
    public void simpleDelete() throws Exception{
        RoleMapper roleMapper = AnnoTesting.getSession().getMapper(RoleMapper.class);
        roleMapper.delete(10);
    }

    @Test
    public void simpleQueryByCondition() throws Exception{
        RoleMapper roleMapper = AnnoTesting.getSession().getMapper(RoleMapper.class);
        Role role = new Role();
        role.setRoleDesc("CEO");
        List<Role> roles = roleMapper.findRoleByCondition(role);
        roles.forEach(temp-> System.out.println(temp));
    }

    @Test
    public void oneToOneBySelectOrderById() throws IOException {
        OrderMapper orderMapper = AnnoTesting.getSession().getMapper(OrderMapper.class);
        Order order = orderMapper.selectOrderById(1);
        System.out.println(order);
    }
    @Test
    public void oneToOneBySelectOrderByIdBaseOfOne() throws IOException {
        OrderMapper orderMapper = AnnoTesting.getSession().getMapper(OrderMapper.class);
        Order order = orderMapper.selectOrderByIdBaseOfOne(1);
        System.out.println(order);
    }

    @Test
    public void oneToOneBySelectOrderByCondition() throws IOException {
        OrderMapper orderMapper = AnnoTesting.getSession().getMapper(OrderMapper.class);
        Order order = new Order();
        order.setId(1);
        List<Order> orderResult = orderMapper.selectOrderByCondition(order);
        System.out.println(Arrays.toString(orderResult.toArray()));
    }

    @Test
    public void oneToManyBySelectUserByCondition() throws IOException {
        UserMapper userMapper = AnnoTesting.getSession().getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        List<User> users = userMapper.selectUserByCondition(user);
        System.out.println(Arrays.toString(users.toArray()));
    }
}
