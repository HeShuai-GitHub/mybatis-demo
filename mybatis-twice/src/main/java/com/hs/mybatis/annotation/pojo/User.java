package com.hs.mybatis.annotation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author heshuai
 * @title: User
 * @description: user实体
 * @date 2021年06月05日 20:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String birthday;
    // 用户所下的所有订单
    private List<Order> orders;
    private List<Role> roles;
}
