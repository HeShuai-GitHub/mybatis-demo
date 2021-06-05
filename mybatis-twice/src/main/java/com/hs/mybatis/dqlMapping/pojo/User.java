package com.hs.mybatis.simple.pojo;

import lombok.Data;

/**
 * @author heshuai
 * @title: User
 * @description: TODO
 * @date 2021年06月05日 20:04
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String birthday;
}
