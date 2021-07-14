package com.hs.mybatis.annotation.mapper;

import com.hs.mybatis.annotation.pojo.Role;
import com.hs.mybatis.annotation.sql.SqlBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author heshuai
 * @title: RoleMapper
 * @description: 在这里利用注解来实现简单的CRUD,在这里可以看出来注解的方式是没有办法直接实现动态Sql的，但是可以通过Sql构造器来间接实现
 *
 * @date 2021年06月05日 22:48
 */
public interface RoleMapper {

    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    Role findOneById(Integer id);

    @Update("UPDATE sys_role SET rolename = #{roleName}, roleDesc = #{roleDesc} WHERE id = #{id}")
    Integer update(Role role);

    @Delete("DELETE FROM sys_role WHERE id = #{id}")
    Integer delete(Integer id);

    @Insert("INSERT INTO sys_role(id,rolename,roleDesc) VALUES(#{id},#{roleName},#{roleDesc})")
    Integer insert(Role role);

    // @SelectProvider() 如果想省略method参数，只需要SqlBuilder构造器实现接口ProviderMethodResolver并且保持方法名一致就可以了，下面这个方法就可以省略method参数，不过为了例子完整没有省略
    @SelectProvider(type = SqlBuilder.class,method = "findRoleByCondition")
    List<Role> findRoleByCondition(Role role);
}
