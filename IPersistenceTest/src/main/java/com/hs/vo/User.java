package com.hs.vo;

import lombok.Data;

/**
 * @author heshuai
 * @title: User
 * @description: 实体类
 * @date 2021年05月14日 22:06
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String birthday;
}
