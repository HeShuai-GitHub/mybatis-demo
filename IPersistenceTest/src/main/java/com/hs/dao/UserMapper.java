package com.hs.dao;

import com.hs.vo.User;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author heshuai
 * @title: UserMapper
 * @description: dao层
 * @date 2021年05月14日 22:02
 */
public interface UserMapper {

    List<User> findAll() throws Exception;

    User findOne(User user) throws Exception;

    Integer updateUserById(User user) throws Exception;

    Integer deleteUserById(String id,String userName) throws Exception;

    Integer insertUser(User user) throws Exception;

}
