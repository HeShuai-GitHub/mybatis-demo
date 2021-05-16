package com.hs.orm.sqlSession;

import com.hs.orm.pojo.Configuration;
import com.hs.orm.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author heshuai
 * @title: DefaultSqlSession
 * @description: 默认Sql会话实现类
 * @date 2021年05月15日 22:08
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    // 这里再次封装一个执行层，用来实际和jdbc打交道
    private Executor executor;
    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor();
    }


    @Override
    public <E> List<E> findALL(String statementId, Object... param) throws Exception {
        MappedStatement mappedStatement = this.configuration.getStatementMap().get(statementId);
        List<Object> resultList = executor.query(this.configuration, mappedStatement,param);
        return (List<E>) resultList;
    }

    @Override
    public <T> T findOne(String statementId, Object... param) throws Exception {
        // 这里可以直接调用findAll来实现查询数据
        List<Object> queryList = findALL(statementId, param);
        if (1 == queryList.size()){
            return (T) queryList.get(0);
        }
        throw new RuntimeException("查询结果过多");
    }

    @Override
    public Integer update(String statementId, Object... param) throws Exception {
        return this.executor.update(this.configuration, this.configuration.getStatementByMap(statementId), param);
    }

    @Override
    public Integer delete(String statementId, Object... param) throws Exception {
        return this.executor.delete(this.configuration, this.configuration.getStatementByMap(statementId), param);
    }

    @Override
    public Integer insert(String statementId, Object... param) throws Exception {
        return this.executor.insert(this.configuration, this.configuration.getStatementByMap(statementId), param);
    }

    @Override
    public <Y> Y getMapper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, (Object proxy, Method method, Object[] args) ->{
            // 调用动态代理对象的方法，会先执行invoke方法，这也是实现Spring AOP的基础
            // 这里拿到该接口的全限定名
            String className = method.getDeclaringClass().getName();
            String methodName = method.getName();
            String key = className+"."+methodName;
            MappedStatement mappedStatement = this.configuration.getStatementMap().get(key);
            switch (mappedStatement.getStatementType()){
                case "select":
                    Type methodReturnType = method.getGenericReturnType();
                    /**
                     *  简单判断该方法返回类型是否是一个集合，来选择执行findAll还是findOne
                     *  ParameterizedType: 参数化类型，也就是List<T> 这种
                     *  ParameterizedType.getRawType() 获得声明类型，如List<User>,将获得List的Type类型，并不是List类型本身
                     *  ParameterizedType.getActualTypeArguments：返回一个参数化类型数组，如Map<String,User>,那么将返回String和User的Type类型
                     *                                              如果将一个non-parameterized type强转成parameterized type并调用此方法，那么将返回一个空数组。
                     * 由上，刚刚在测试的时候遇到一个问题，如((ParameterizedType)methodReturnType).getRawType() instanceof List始终为false，
                     * 这是因为getRawType()得到是一个Type类型并不是List类型，只是这个Type类型描述了List类型的信息，所以为false
                     */
                    if (methodReturnType instanceof ParameterizedType){
                        return this.findALL(key,args);
                    }else{
                        return this.findOne(key,args);
                    }
                case "update":
                    return this.update(key,args);
                case "delete":
                    return this.delete(key,args);
                case "insert":
                    return this.insert(key,args);
            }
            return null;
        });
        return (Y) proxyInstance;
    }

}
