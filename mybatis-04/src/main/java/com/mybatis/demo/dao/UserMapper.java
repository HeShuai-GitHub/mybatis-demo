package com.mybatis.demo.dao;

import com.mybatis.demo.model.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 一对一对应关系
 */
public interface UserMapper {

    /**
     * 子查询一对一对应关系
     * @return
     */
    List<User> selectAll();

    /**
     * 查询集合一对一对应关系
     * @return
     */
    List<User> selectAll1();

}
