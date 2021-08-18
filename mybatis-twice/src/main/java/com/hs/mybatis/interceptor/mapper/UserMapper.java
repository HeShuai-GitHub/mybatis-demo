package com.hs.mybatis.interceptor.mapper;

import com.hs.mybatis.annotation.pojo.User;
import com.hs.mybatis.annotation.sql.SqlBuilder;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author heshuai
 * @title: UserMapper
 * @description: 展示一对多和多对多的映射关系
 * @date 2021年06月05日 20:40
 */
public interface UserMapper extends Mapper<User> {

    // 没办法映射一对多
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "orders", column = "id", many = @Many(
                    select = "com.hs.mybatis.annotation.mapper.OrderMapper.selectById"
            ))
    })
    @SelectProvider(type = SqlBuilder.class)
    List<User> selectUserByCondition(User user);


    @Select("SELECT `user`.id AS id, `user`.username AS username, `user`.password AS password, `user`.birthday AS birthday " +
            "FROM `user` `user` " +
            "WHERE `user`.id = #{id} ")
    User findUserById(Integer id);


    @Select("SELECT `user`.id AS id, `user`.username AS username, `user`.password AS password, `user`.birthday AS birthday " +
            "FROM `user` `user` " )
    List<User> findAll();

}
