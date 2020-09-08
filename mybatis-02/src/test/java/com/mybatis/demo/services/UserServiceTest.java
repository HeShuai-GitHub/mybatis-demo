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

    /**
     * 查询全部
     */
    @Test
    public void selectAll(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        //调用查询
        List<User> users= userMapper.selectAll();
        for (User user:users){
            System.out.println(user);
        }
    }

    /**
     * 根据id查询
     */
    @Test
    public void selectOne(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        //调用查询
        User user= userMapper.selectOne(1);
        System.out.println(user);
    }


    /**
     * 修改信息
     */
    @Test
    public void update(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        User user=new User();
        user.setId(2);
        user.setSex("男");
        //调用查询
        Integer count= userMapper.update(user);
        if (count>0){
            System.out.println("修改成功");
        }
        //增删改时需要提交事务
        sqlSession.commit();
    }

    /**
     * 删除信息
     */
    @Test
    public void delete(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        Integer count= userMapper.delete(1);
        if (count>0){
            System.out.println("删除成功");
        }
        //增删改时需要提交事务
        sqlSession.commit();
    }

    /**
     * 插入信息
     */
    @Test
    public void insert(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        User user=new User();
        user.setId(1);
        user.setName("张三");
        user.setSex("女");
        //调用查询
        Integer count= userMapper.insert(user);
        if (count>0){
            System.out.println("添加成功");
        }
        //增删改时需要提交事务
        sqlSession.commit();
    }
} 
