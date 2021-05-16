package com.hs.orm.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author heshuai
 * @title: SqlSession
 * @description: SqlSession会话，里边包含对数据库CRUD操作和动态生成dao的代理实现类
 * @date 2021年05月15日 22:02
 */
public interface SqlSession {

    <E> List<E> findALL(String statementId, Object... param) throws Exception;

    <T> T findOne(String statementId, Object... param) throws Exception;

    Integer update(String statementId, Object... param) throws Exception;

    Integer delete(String statementId, Object... param) throws Exception;

    Integer insert(String statementId, Object... param) throws Exception;

    // 获得指定dao层接口实现类，这里使用的是jdk动态代理
    <Y> Y getMapper(Class<?> mapperClass);
}
