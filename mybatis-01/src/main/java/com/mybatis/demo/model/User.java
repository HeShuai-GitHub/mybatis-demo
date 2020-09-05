package com.mybatis.demo.model;

/**
 * @program: mybatis-demo
 * @description: user表model
 * @author: hs
 * @create: 2020-09-03 11:00
 **/
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
