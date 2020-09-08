package com.mybatis.demo.dao;

import com.mybatis.demo.model.User;
import org.apache.ibatis.annotations.Select;
import org.omg.CORBA.INTERNAL;

import java.util.List;

public interface UserMapper {

    /**
     * 查询所有的数据
     * @return 全部用户数据
     * 注解式开发
     */
    @Select("select * from `user`")
    List<User> selectAll();

    /**
     * 根据id查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    User selectOne(Integer id);

    /**
     * 分页查询数据
     * @param skewing 偏移量
     * @param count 每一页中的数量
     * @return 返回分页后用户集合
     */
    List<User> selectLimit(Integer skewing,Integer count);

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
