package com.hs.mybatis.annotation.sql;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import com.hs.mybatis.annotation.pojo.Order;
import com.hs.mybatis.annotation.pojo.Role;
import com.hs.mybatis.annotation.pojo.User;
import com.mysql.cj.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;
import org.apache.logging.log4j.util.Strings;

/**
 * @author heshuai
 * @title: SqlBuilder
 * @description: SQL语言构造器，当所在的Sql构造器实现了ProviderMethodResolver接口后，在使用类似于@SelectProvider注解时可以省略method参数，如果省略了这个参数，那么将
 *              查询制定构造器类的同样方法，如果没有找到同名方法，那么将搜索provideSql方法进行使用
 * @date 2021年06月05日 23:34
 */
public  class SqlBuilder implements ProviderMethodResolver {

    /**
     * 这个@Param可以写，也可以不写，如果不写的情况下，必须这个SQL构造方法和Mapper中的方法参数个数、顺序、类型保持一直，
     * 如果加上@Param的话，那么可以只写自己需要的参数
     */
    public static String findRoleByCondition(@Param("role") final Role role) {
      return new SQL(){{
       SELECT("*");
       FROM("sys_role");
       if (null != role.getId())
           WHERE("id = #{id}");
       if (Strings.isNotBlank(role.getRoleName()))
           WHERE("rolename = #{roleName}");
       if (Strings.isNotBlank(role.getRoleDesc()))
           WHERE("roleDesc = #{roleDesc}");
      }}.toString();
    }



    public static String selectOrderByCondition(final com.hs.mybatis.annotation.pojo.Order order){
        return new SQL(){{
            SELECT("`id`, `ordertime`, `total`, `uid`");
            FROM("`orders`");
            if (null != order.getId())
                WHERE("id = #{id}");
            if (Strings.isNotBlank(order.getOrderTime()))
                WHERE("ordertime = #{orderTime}");
            if (null != order.getTotal())
                WHERE("total = #{total}");
        }}.toString();
    }

    public static String selectUserByCondition(final User user){
        return new SQL(){{
            SELECT("`u`.* ");
            FROM("`user` u");
            if (null != user.getId())
                WHERE(" u.id = #{id}");
            if (null != user.getUsername())
                WHERE("u.username = #{username}");
            if (null != user.getBirthday())
                WHERE("u.birthday = #{birthday}");
            if (null != user.getPassword())
                WHERE("u.password = #{password}");
        }}.toString();
    }

    public static String updateOrder(final Order order){
        return new SQL(){{
            UPDATE("orders");
            if (null != order.getOrderTime())
                SET("ordertime = " + order.getOrderTime());
            if (null != order.getTotal())
                SET("total = " + order.getTotal());
            if (null != order.getUid())
                SET("uid = " + order.getUid());
            WHERE("id = " + order.getId());
        }}.toString();
    }

}
