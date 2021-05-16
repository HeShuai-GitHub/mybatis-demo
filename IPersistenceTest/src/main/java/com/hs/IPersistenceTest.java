package com.hs;

import com.hs.dao.UserMapper;
import com.hs.dao.UserMapperImpl;
import com.hs.orm.io.Resource;
import com.hs.orm.sqlSession.SqlSession;
import com.hs.orm.sqlSession.SqlSessionFactory;
import com.hs.orm.sqlSession.SqlSessionFactoryBuilder;
import com.hs.vo.User;
import org.dom4j.DocumentException;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.sound.midi.Soundbank;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author heshuai
 * @title: IPersistenceTest
 * @description: 测试自定义ORM框架的CRUD
 * @date 2021年05月14日 22:13
 */
public class IPersistenceTest {

    private UserMapper userMapper = new SqlSessionFactoryBuilder().build(Resource.getResourceAsStream("SQLMapConfig.xml")).openSession().getMapper(UserMapper.class);

    public IPersistenceTest() throws PropertyVetoException, DocumentException {
    }

    @Test
    public void query() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
        System.out.println(userMapper.findOne(user));
        System.out.println("*********************************");
        System.out.println(userMapper.findAll());
    }

    @Test
    public void insert() throws Exception{
        User user = new User();
        user.setId(4);
        user.setUsername("小红");
        user.setPassword("456");
        user.setBirthday("ddd");
        System.out.println(userMapper.insertUser(user));
    }
    @Test
    public void delete() throws Exception{
        System.out.println(userMapper.deleteUserById("4","小黑"));
    }

    @Test
    public void update() throws Exception{
        User user = new User();
        user.setId(4);
        user.setUsername("小黑");
        user.setPassword("000");
        user.setBirthday("ooo");
        System.out.println(userMapper.updateUserById(user));
    }


}
