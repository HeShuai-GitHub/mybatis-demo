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
     * choose、when和trim标签
     * @param user
     * @return
     */
    List<User> selectChoose(User user);

    /**
     * foreach标签
     * @param ids id 集合
     * @return
     */
    List<User> selectForeach(Integer []ids);

    /**
     * set 标签
     * @param user
     * @return
     */
    Integer updateSet(User user);

}
