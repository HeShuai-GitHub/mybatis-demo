package com.hs.mybatis.dqlMapping.mapper;

import com.hs.mybatis.dqlMapping.pojo.Order;

import java.util.List;

/**
 * @author heshuai
 * @title: OrderMapper.xml
 * @description: 展示一对一映射
 * @date 2021年06月05日 20:42
 */
public interface OrderMapper {
    Order selectOrderById(Integer id);

    List<Order> selectOrderByCondition(Order order);
}
