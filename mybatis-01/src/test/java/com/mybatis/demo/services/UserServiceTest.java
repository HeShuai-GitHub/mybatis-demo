package com.mybatis.demo.services;

import com.mybatis.demo.dao.UserDao;
import com.mybatis.demo.model.User;
import com.mybatis.demo.utils.GetSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        //调用查询
        List<User> users=userDao.selectAll();
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
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        //调用查询
        User user=userDao.selectOne(1);
        System.out.println(user);
    }

    /**
     * 根据map对象查询
     */
    @Test
    public void selectMap(){
        //获得Mapper对象
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("id","1");
        //模糊查询的时候，最好在业务层拼接通配符，防止sql注入
        map.put("name","%李%");
        //调用查询
        List<User> users=userDao.selectByMap(map);
        for (User user:users){
            System.out.println(user);
        }
    }

    /**
     * 修改信息
     */
    @Test
    public void update(){
        //获得Mapper对象
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        User user=new User();
        user.setId(2);
        user.setSex("男");
        //调用查询
        Integer count=userDao.update(user);
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
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        Integer count=userDao.delete(1);
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
        UserDao userDao=sqlSession.getMapper(UserDao.class);
        User user=new User();
        user.setId(1);
        user.setName("张三");
        user.setSex("女");
        //调用查询
        Integer count=userDao.insert(user);
        if (count>0){
            System.out.println("添加成功");
        }
        //增删改时需要提交事务
        sqlSession.commit();
    }
} 
