package com.hs.mybatis.annotation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author heshuai
 * @title: Order
 * @description: order实体
 * @date 2021年06月05日 20:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private Integer id;
    private String orderTime;
    private BigDecimal total;
    private Integer uid;
    // 该订单所属的用户
    private User user;
}
