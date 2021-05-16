package com.hs.orm.sqlSession;

/**
 * @author heshuai
 * @title: SqlSessionFactory
 * @description: 在工厂模式中，生成sqlSession会话
 * @date 2021年05月15日 20:57
 */
public interface SqlSessionFactory {

    SqlSession openSession();

}
