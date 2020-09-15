package com.mybatis.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

/**
 * @program: mybatis-demo
 * @description: user表model
 * @Alias("user"):mybatis中设置别名
 * @Data 增加setter、getter、constructor、toString、hasCode方法
 * @NoArgsConstructor 增加无参构造方法
 * @AllArgsConstructor 增加有参构造方法
 * @author: hs
 * @create: 2020-09-03 11:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String name;
    /**
     * 和数据库字段名有不同，可以使用mybatis xml配置中的resultMap方式进行映射
     */
    private String sex;

}
