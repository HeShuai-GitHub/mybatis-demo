package com.mybatis.demo.services;

import com.mybatis.demo.dao.RoleMapper;
import com.mybatis.demo.dao.UserMapper;
import com.mybatis.demo.model.Role;
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
     * 查询用户表全部
     */
    @Test
    public void selectUserAll(){
        //获得Mapper对象
        UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
        //调用查询
//        List<User> users= userMapper.selectAll();
        List<User> users= userMapper.selectAll1();
        for (User user:users){
            System.out.println(user);
        }
    }

    /**
     * 查询角色表全部
     */
    @Test
    public void selectRoleAll(){
        //获得Mapper对象
        RoleMapper roleMapper =sqlSession.getMapper(RoleMapper.class);
        //调用查询
        List<Role> roles= roleMapper.selectAll();
        for (Role role:roles){
            System.out.println(role);
        }
    }

}
