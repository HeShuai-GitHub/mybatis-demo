package com.hs.mybatis.annotation.pojo;

import lombok.Data;

/**
 * @author heshuai
 * @title: Role
 * @description: role实体
 * @date 2021年06月05日 20:39
 */
@Data
public class Role {
    private Integer id;
    private String roleName;
    private String roleDesc;
}
