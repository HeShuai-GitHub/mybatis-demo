package com.hs.mybatis.annotation.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author heshuai
 * @title: Role
 * @description: role实体
 * @date 2021年06月05日 20:39
 */
@Data
@ToString
public class Role implements Serializable {
    private Integer id;
    private String roleName;
    private String roleDesc;
}
