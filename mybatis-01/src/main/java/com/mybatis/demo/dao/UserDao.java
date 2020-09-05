package com.mybatis.demo.dao;

import com.mybatis.demo.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    /**
     * 查询所有的数据
     * @return 全部用户数据
     */
    List<User> selectAll();

    /**
     * 根据id查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    User selectOne(Integer id);

    /**
     * 根据map key值来传输参数值
     * @param map 参数
     * @return 用户列表
     */
    List<User> selectByMap(Map<String,Object> map);

    /**
     * 插入一条用户数据
     * @param user 用户数据
     * @return 返回插入条数
     */
    Integer insert(User user);

    /**
     * 修改一条用户数据
     * @param user 用户数据
     * @return 返回修改条数
     */
    Integer update(User user);

    /**
     * 删除一条数据
     * @param id 用户id
     * @return 返回删除条数
     */
    Integer delete(Integer id);
}
