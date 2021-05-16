package com.hs.orm.sqlSession;

import com.hs.orm.pojo.Configuration;
import com.hs.orm.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author heshuai
 * @title: Executor
 * @description: 执行器，实际对数据库的操作
 * @date 2021年05月15日 18:05
 */
public interface Executor {

    <E>List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... param) throws Exception;

    Integer update(Configuration configuration, MappedStatement mappedStatement, Object... param) throws Exception;

    Integer delete(Configuration configuration, MappedStatement mappedStatement, Object... param) throws Exception;

    Integer insert(Configuration configuration, MappedStatement mappedStatement, Object... param) throws Exception;

}
