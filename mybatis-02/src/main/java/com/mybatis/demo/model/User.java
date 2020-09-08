package com.mybatis.demo.model;

import org.apache.ibatis.type.Alias;

/**
 * @program: mybatis-demo
 * @description: user表model
 * 开 mybatis-config中设置别名
 * @author: hs
 * @create: 2020-09-03 11:00
 **/
@Alias("user")
public class User {

    private Integer id;

    private String name;

    private String sex;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
