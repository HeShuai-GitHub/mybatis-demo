package com.hs.orm.pojo;

import lombok.Data;

/**
 * @author heshuai
 * @title: MapperdStatement
 * @description: 将Mapper.xml封装成为该对象
 * @date 2021年05月14日 22:33
 */
@Data
public class MappedStatement {
    // id标识
    private String id;
    // 声明类型：增删改查
    private String statementType;
    // 入参类型
    private String parameterType;
    // 返回值类型
    private String resultType;
    // sql语句
    private String sql;
}
