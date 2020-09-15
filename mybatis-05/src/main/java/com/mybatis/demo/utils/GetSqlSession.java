package com.mybatis.demo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: mybatis-demo
 * @description: mybatis 获取环境工具类
 * 每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。
 * SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得。
 * 而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先配置的
 * Configuration 实例来构建出 SqlSessionFactory 实例
 * @author: hs
 * @create: 2020-08-22 09:33
 **/
public class GetSqlSession {

    private static SqlSessionFactory sqlSessionFactory =null;
    static {
        String resource = "mybatis-config.xml";
        try {
            InputStream  inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从 SqlSessionFactory 中获取 SqlSession,SqlSession 提供了在数据库执行 SQL 命令所需的所有方法
     * @return
     */
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }


}
