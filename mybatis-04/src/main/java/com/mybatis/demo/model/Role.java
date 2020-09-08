package com.mybatis.demo.model;

import lombok.Data;

import java.util.List;

/**
 * @program: mybatis-demo
 * @description: 角色表
 * @author: hs
 * @create: 2020-09-08 11:56
 **/
@Data
public class Role {

    private Integer id;

    private String name;

    private List<User> users;
}
