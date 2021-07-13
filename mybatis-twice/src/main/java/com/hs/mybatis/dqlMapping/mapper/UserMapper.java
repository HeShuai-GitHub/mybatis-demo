package com.hs.mybatis.dqlMapping.mapper;

import com.hs.mybatis.dqlMapping.pojo.User;

import java.util.List;

/**
 * @author heshuai
 * @title: UserMapper
 * @description: 展示一对多和多对多的映射关系
 * @date 2021年06月05日 20:40
 */
public interface UserMapper {

    List<User> selectUserByCondition(User user);

    List<User> selectUserAndRoleALl();

}
