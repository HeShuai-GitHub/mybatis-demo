package com.hs.orm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heshuai
 * @title: BoundSql
 * @description: 将解析后的sql封装到该对象中
 * @date 2021年05月15日 18:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoundSql {
    /**
     * 经过解析后将#{}替换成?占位符
     * 如   SELECT id,username,password,birthday FROM `user` WHERE id = #{id} AND username = #{username};
     * 变成：SELECT id,username,password,birthday FROM `user` WHERE id = ? AND username = ?;
     */
    private String sqlText;
    /**
     * 存放解析出来的#{}中的值
     */
    private List<ParameterMapping> parameterMappings = new ArrayList<>();

}
