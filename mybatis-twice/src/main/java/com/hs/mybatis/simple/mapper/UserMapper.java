package com.hs.mybatis.simple.mapper;

import com.hs.mybatis.simple.pojo.User;

import java.util.List;

/**
 * @author heshuai
 * @title: dd
 * @description: TODO
 * @date 2021年06月05日 20:05
 */
public interface UserMapper {

    List<User> findAll() throws Exception;

    User findOne(User user) throws Exception;

    Integer updateUserById(User user) throws Exception;

    Integer deleteUserById(String id,String userName) throws Exception;

    Integer insertUser(User user) throws Exception;
}
