package com.hs.orm.sqlSession;

import com.hs.orm.pojo.Configuration;

/**
 * @author heshuai
 * @title: DefaultSqlSessionFactory
 * @description: 默认会话工厂实现类
 * @date 2021年05月15日 21:29
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;
    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(this.configuration);
    }
}
