package com.hs.dao;

import com.hs.orm.io.Resource;
import com.hs.orm.sqlSession.SqlSession;
import com.hs.orm.sqlSession.SqlSessionFactory;
import com.hs.orm.sqlSession.SqlSessionFactoryBuilder;
import com.hs.vo.User;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author heshuai
 * @title: UserMapperImpl
 * @description: dao层user接口实现类，该类在使用过程中存在一些弊端，比如：
 *                      1、openSession代码重复
 *                      2、硬编码问题，主要是指statementId,如"com.hs.dao.UserMapper.findOne"写死在程序中，当对应mapper Xml文件改变时，代码必须做出改变
 *               这类问题主要的解决办法就是，使用代理模式来动态生成userMapper接口的代理实现类来规避上面的问题
 * @date 2021年05月15日 19:40
 */
public class UserMapperImpl /*implements UserMapper*/{
    private SqlSessionFactory sqlSessionFactory =  new SqlSessionFactoryBuilder().build(Resource.getResourceAsStream("SQLMapConfig.xml"));

    public UserMapperImpl() throws PropertyVetoException, DocumentException {
    }

    public List<User> findAll() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> result = sqlSession.findALL("com.hs.dao.UserMapper.findAll");
        return result;
    }

    public User findOne(User user) throws Exception{
        User result = sqlSessionFactory.openSession().findOne("com.hs.dao.UserMapper.findOne", user);
        return result;
    }
}
