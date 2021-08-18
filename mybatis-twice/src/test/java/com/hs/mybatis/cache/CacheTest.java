package com.hs.mybatis.cache;

import com.hs.mybatis.annotation.mapper.OrderMapper;
import com.hs.mybatis.annotation.mapper.RoleMapper;
import com.hs.mybatis.annotation.pojo.Order;
import com.hs.mybatis.annotation.pojo.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author heshuai
 * @title: CacheTest
 * @description:
 * @date 2021年07月14日 21:47
 */
public class CacheTest {

    private SqlSessionFactory factory;

    @Before
    public void before() throws IOException {
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("annotation/SqlConfig.xml"));
    }

    @Test
    public void oneCache(){
        SqlSession sqlSession = this.factory.openSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> orders1 = orderMapper.selectById(1);
        System.out.println(Arrays.toString(orders1.toArray()));
        List<Order> orders2 = orderMapper.selectById(1);
        System.out.println(Arrays.toString(orders2.toArray()));
        // 一级缓存 缓存的是查询出的对象引用
        System.out.println(orders1 == orders2);
    }

    @Test
    public void oneCacheFlush(){
        SqlSession sqlSession = this.factory.openSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> orders1 = orderMapper.selectById(1);
        System.out.println(Arrays.toString(orders1.toArray()));
        // 更新数据或者手动刷新都会导致一级缓存刷新
//        Order order = new Order();
//        order.setId(1);
//        order.setTotal(new BigDecimal(50));
//        orderMapper.updateOrder(order);
//        sqlSession.commit();
        sqlSession.clearCache();
        List<Order> orders2 = orderMapper.selectById(1);
        System.out.println(Arrays.toString(orders2.toArray()));
        // 一级缓存 缓存的是查询出的对象引用
        System.out.println(orders1 == orders2);
    }

    /**
     * 二级缓存和一级缓存同理，不过在域上面是不一样的，比如二级缓存是以mapper为一个维度，所有SqlSession同享一个二级缓存
     */
    @Test
    public void twoCache(){
        SqlSession sqlSession1 = this.factory.openSession();
        RoleMapper roleMapper1 = sqlSession1.getMapper(RoleMapper.class);
        SqlSession sqlSession2 = this.factory.openSession();
        RoleMapper roleMapper2 = sqlSession2.getMapper(RoleMapper.class);
        SqlSession sqlSession3 = this.factory.openSession();
        RoleMapper roleMapper3 = sqlSession3.getMapper(RoleMapper.class);

        Role role1 = roleMapper1.findOneById(1);
        System.out.println(role1);
        sqlSession1.close();

        Role role2 = roleMapper2.findOneById(1);
        System.out.println(role2);
        Role role3 = roleMapper3.findOneById(1);
        System.out.println(role3);
        // 二级缓存 缓存的是查询出的对象序列化数据
        System.out.println(role1 == role2);
    }
}
