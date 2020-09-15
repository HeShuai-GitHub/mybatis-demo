package com.mybatis.demo.services;

import com.mybatis.demo.dao.UserMapper;
import com.mybatis.demo.model.User;
import com.mybatis.demo.utils.GetSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.management.relation.Role;
import java.sql.Array;
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

    @Test
    public void selectAll(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        //调用查询
//        List<User> users= userMapper.selectAll();
        List<User> users= userMapper.selectAll(user);
        for (User user1:users){
            System.out.println(user1);
        }
    }

    @Test
    public void selectChoose(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        user.setSex("男");
        //调用查询
//        List<User> users= userMapper.selectAll();
        List<User> users= userMapper.selectChoose(user);
        for (User user1:users){
            System.out.println(user1);
        }
    }

    @Test
    public void selectForeach(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        Integer [] ads={1,2};
        List<User> users= userMapper.selectForeach(ads);
        for (User user1:users){
            System.out.println(user1);
        }
    }

    @Test
    public void insertSet(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        user.setSex("男");
        userMapper.updateSet(user);
        sqlSession.commit();
    }

}
