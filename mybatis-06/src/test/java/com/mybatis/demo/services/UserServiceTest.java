package com.mybatis.demo.services;

import com.mybatis.demo.dao.UserMapper;
import com.mybatis.demo.model.User;
import com.mybatis.demo.utils.GetSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/** 
* UserService Tester. 
* 
* @author <Authors name> 
* @since <pre>9�� 3, 2020</pre> 
* @version 1.0 
*/ 
public class UserServiceTest { 

    private SqlSession sqlSession=null;

    @Before
    public void before() throws Exception {
        sqlSession= GetSqlSession.getSqlSession();
    }

    @After
    public void after() throws Exception {
        sqlSession.close();
    }

    /*一级缓存*/
    @Test
    public void selectOne(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        List<User> users= userMapper.selectAll(user);
        for (User user1:users){
            System.out.println(user1);
        }
        /*sqlSession被关闭后，一级缓存失效就会重新查询数据库数据了*/
        System.out.println("=============默认开启一级缓存，将数据缓存到SqlSession中=============");
        users= userMapper.selectAll(user);
        for (User user1:users){
            System.out.println(user1);
        }

    }

    /*二级缓存*/
    @Test
    public void selectTwo(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        List<User> users= userMapper.selectAll(user);
        for (User user1:users){
            System.out.println(user1);
        }
        /*关闭sqlSession*/
        sqlSession.close();
        /*重新获取userMapper*/
        sqlSession= GetSqlSession.getSqlSession();
        userMapper =sqlSession.getMapper(UserMapper.class);

        System.out.println("=============开机二级缓存=============");
        users= userMapper.selectAll(user);
        for (User user1:users){
            System.out.println(user1);
        }

    }


}
