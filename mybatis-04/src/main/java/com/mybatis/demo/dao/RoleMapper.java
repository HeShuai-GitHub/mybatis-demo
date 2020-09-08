package com.mybatis.demo.dao;

import com.mybatis.demo.model.Role;

import java.util.List;

/**
 * 多对一对应关系
 */
public interface RoleMapper {

    List<Role> selectAll();

}
