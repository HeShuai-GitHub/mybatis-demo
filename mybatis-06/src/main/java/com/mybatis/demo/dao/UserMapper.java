package com.mybatis.demo.dao;

import com.mybatis.demo.model.User;

import java.util.List;

/**
 * 一对一对应关系
 */
public interface UserMapper {

    /**
     * if和where标签
     * @return
     */
    List<User> selectAll(User user);

    /**
     * set 标签
     * @param user
     * @return
     */
    Integer updateSet(User user);

}
