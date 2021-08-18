package com.hs.mybatis.interceptor;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hs.mybatis.annotation.pojo.User;
import com.hs.mybatis.interceptor.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author heshuai
 * @title: InterceptorTesting
 * @description: TODO
 * @date 2021年08月17日 18:09
 */
public class InterceptorTesting {


    public SqlSession getSqlSession() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("interceptor/SqlConfig.xml"));
        return factory.openSession(true);
    }

    /**
     * 自定义插件测试
     * @throws IOException
     */
    @Test
    public void myInterceptorTest() throws IOException {
        UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
        System.out.println(userMapper.findUserById(1));
    }

    /**
     * 分页插件测试
     * @throws IOException
     */
    @Test
    public void pageHelperTest() throws IOException {
        UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
        PageHelper.startPage(2, 2);
        List<User> all = mapper.findAll();
        PageInfo<User> pageInfo = new PageInfo<User>(all);
        System.out.println("分页信息：");
        System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.getStartRow());
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getPageSize());
        System.out.println("分页信息结束");
        System.out.println(Arrays.toString(all.toArray()));
    }

    /**
     * 通用mapper测试
     * @throws IOException
     */
    @Test
    public void mapperTest() throws IOException {
        UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
        User user = new User();
        user.setBirthday("1");
        user.setPassword("123");
        user.setUsername("名字");
        // 通用Mapper方法
        mapper.insert(user);
        List<User> all = mapper.findAll();
        System.out.println(Arrays.toString(all.toArray()));
    }

}
