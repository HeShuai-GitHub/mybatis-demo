package com.hs.mybatis.annotation.mapper;

import com.hs.mybatis.annotation.sql.SqlBuilder;
import com.hs.mybatis.annotation.pojo.Order;
import com.hs.mybatis.annotation.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author heshuai
 * @title: OrderMapper.xml
 * @description: 展示一对一映射
 * @date 2021年06月05日 20:42
 */
public interface OrderMapper {
    @Select("SELECT `order`.*, `user`.id AS user_id, `user`.username AS user_username, `user`.password AS user_password, `user`.birthday AS user_birthday " +
            "FROM `user` `user` " +
            "RIGHT JOIN `orders` `order` " +
            "ON `order`.uid = `user`.id " +
            "WHERE `order`.id = #{id} ")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "total", column = "total"),
            @Result(property = "user.id", column = "user_id"),
            @Result(property = "user.username", column = "user_username"),
            @Result(property = "user.password", column = "user_password"),
            @Result(property = "user.birthday", column = "user_birthday"),
    })
    Order selectOrderById(Integer id);

    @Select("SELECT id, ordertime, total, uid " +
            "FROM `orders` " +
            "WHERE id = #{id} ")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "total", column = "total"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "user", column = "uid", javaType = User.class,
                    one = @One(select = "com.hs.mybatis.annotation.mapper.UserMapper.findUserById",fetchType = FetchType.LAZY)),
    })
    Order selectOrderByIdBaseOfOne(Integer id);

    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "total", column = "total"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "user", column = "uid", javaType = User.class,
                    one = @One(select = "com.hs.mybatis.annotation.mapper.UserMapper.findUserById",fetchType = FetchType.LAZY)),
    })
    @SelectProvider(type = SqlBuilder.class)
    List<Order> selectOrderByCondition(Order order);

    @Select("SELECT id, ordertime AS orderTime, total, uid FROM orders WHERE uid = #{id} ")
    List<Order> selectById(Integer uid);
}
