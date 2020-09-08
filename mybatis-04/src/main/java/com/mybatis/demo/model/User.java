package com.mybatis.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

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
    private String sex1;

    /**
     * 在角色权限控制中，用户和角色之间的关系应该是多对多的，但是目前为了充分展示
     * mybatis的一对多和多对一，这里就假定一个用户对应一个角色，一个角色可以和多个用户对应
     */
    private Role role;

}
