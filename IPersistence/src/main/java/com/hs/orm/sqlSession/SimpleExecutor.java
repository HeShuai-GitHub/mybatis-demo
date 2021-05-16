package com.hs.orm.sqlSession;

import com.hs.orm.pojo.BoundSql;
import com.hs.orm.pojo.Configuration;
import com.hs.orm.pojo.MappedStatement;
import com.hs.orm.pojo.ParameterMapping;
import com.hs.orm.utils.GenericTokenParser;
import com.hs.orm.utils.ParameterMappingTokenHandler;
import com.hs.orm.utils.PrimitiveAndWrapUtil;
import com.mysql.cj.util.StringUtils;

import javax.lang.model.type.PrimitiveType;
import javax.sql.DataSource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * @author heshuai
 * @title: SimpleExecutor
 * @description: 实际对数据库的操作
 * @date 2021年05月15日 18:05
 */
public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... param) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, InstantiationException, IntrospectionException {
        // 1、使用数据源连接池来获取连接
        Connection connection = configuration.getDataSource().getConnection();
        /**
         * 2、解析sql
         * xml中配置的sql语句，类似于SELECT id,username,password,birthday FROM `user` WHERE id = #{id} AND username = #{username};
         * 在这里需要对有参数的#{}进行解析，并将参数的值匹配到对应位置
         */
        String sql = mappedStatement.getSql();
        // 完成sql的解析工作
        BoundSql boundSql = getBoundSql(sql);
        // 3、生成预编译对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        // 4、设置参数
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Boolean isPrimitive = PrimitiveAndWrapUtil.isPrimitiveAndWrapClass(mappedStatement.getParameterType()); // 是否是基础数据类型
        Class<?> parameterType =null;
        if (!isPrimitive)
            // 参数类型
            parameterType = getClassType(mappedStatement.getParameterType());
        for (int i = 0; i < parameterMappings.size(); i++) {
            if (param.length<1){
                throw new SQLException("该SQL命令需要参数");
            }
            if (isPrimitive){
                // 基础数据类型|其包装类型|String，默认按照顺序设置参数
                preparedStatement.setObject(i+1,param[i]);
            }else{
                // #{}中的值
                String fieldName = parameterMappings.get(i).getContent();
                /**
                 * 这里假定param参数是一个实体对象，并且实体中每个属性名和#{}相匹配
                 * 通过反射获取param参数对象中属性值并赋值到jdbc的参数中
                 */
                Field field = parameterType.getDeclaredField(fieldName);
                // 暴力访问private域的属性值
                field.setAccessible(true);
                // 获取属性的值
                Object fieldValue = field.get(param[0]);
                preparedStatement.setObject(i+1,fieldValue);
            }
        }
        // 5、执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        // 6、封装返回对象
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType); // 返回值类型
        List<Object> resultData = new ArrayList<>();  // 最终结果集
        while (resultSet.next()) {
            Object result = resultTypeClass.getConstructor().newInstance();
            // 返回结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 结果集中列的数量
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                /* 通过反射对对象属性进行封装
                Field field = resultTypeClass.getDeclaredField(columnName);
                field.setAccessible(true);
                // 为属性赋值
                field.set(result,resultSet.getObject(columnName));
                */
                // 通过内省对对象属性进行封装
                PropertyDescriptor descriptor = new PropertyDescriptor(columnName, resultTypeClass);
                // 获得写方法并进行封装
                Method writeMethod = descriptor.getWriteMethod();
                writeMethod.invoke(result,resultSet.getObject(columnName));
            }
            resultData.add(result);
        }
        return (List<E>) resultData;
    }

    @Override
    public Integer update(Configuration configuration, MappedStatement mappedStatement, Object... param) throws Exception {
        // 1、获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2、解析sql
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        String sqlText = boundSql.getSqlText();
        // 3、生成预编译对象
        PreparedStatement preparedStatement = connection.prepareStatement(sqlText);
        // 4、设置参数
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Boolean isPrimitive = PrimitiveAndWrapUtil.isPrimitiveAndWrapClass(mappedStatement.getParameterType()); // 是否是基础数据类型
        Class<?> parameterType =null;
        if (!isPrimitive)
            parameterType = getClassType(mappedStatement.getParameterType());
        for (int i = 0; i < parameterMappings.size(); i++) {
            if (param.length<1){
                throw new SQLException("该SQL命令需要参数");
            }
            if (isPrimitive){
                // 基础数据类型|其包装类型|String，默认按照顺序设置参数
                preparedStatement.setObject(i+1,param[i]);
            }else{
                String fieldName = parameterMappings.get(i).getContent();
                Field field = parameterType.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(param[0]);
                preparedStatement.setObject(i+1,fieldValue);
            }
        }
        // 5、执行sql
        int successRowCount = preparedStatement.executeUpdate(); // 成功的条数
        // 6、封装结果集
        // 因为是修改记录，所以返回的是修改条数，不需要封装结果集
        return successRowCount;
    }

    @Override
    public Integer delete(Configuration configuration, MappedStatement mappedStatement, Object... param) throws Exception {
        // 1、获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2、解析sql
        BoundSql boundSql = getBoundSql(mappedStatement.getSql());
        // 3、生成预编译对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        // 4、设置参数
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Boolean isPrimitive = PrimitiveAndWrapUtil.isPrimitiveAndWrapClass(mappedStatement.getParameterType()); // 是否是基础数据类型
        Class<?> parameterType =null;
        if (!isPrimitive)
            parameterType = getClassType(mappedStatement.getParameterType());
        for (int i = 0; i < parameterMappings.size(); i++) {
            if (param.length<1){
                throw new SQLException("该SQL命令需要参数");
            }
            if (isPrimitive){
                // 基础数据类型|其包装类型|String，默认按照顺序设置参数
                preparedStatement.setObject(i+1,param[i]);
            }else{
                String fieldName = parameterMappings.get(i).getContent();
                Field field = parameterType.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(param[0]);
                preparedStatement.setObject(i+1,fieldValue);
            }
        }
        // 5、执行sql
        int successRowCount = preparedStatement.executeUpdate();
        // 6、封装结果集
        return successRowCount;
    }

    @Override
    public Integer insert(Configuration configuration, MappedStatement mappedStatement, Object... param) throws Exception {
        // 1、获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2、解析sql
        BoundSql boundSql = getBoundSql(mappedStatement.getSql());
        // 3、生成预编译对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        // 4、设置参数
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Boolean isPrimitive = PrimitiveAndWrapUtil.isPrimitiveAndWrapClass(mappedStatement.getParameterType()); // 是否是基础数据类型
        Class<?> parameterType =null;
        if (!isPrimitive)
            parameterType = getClassType(mappedStatement.getParameterType());
        for (int i = 0; i < parameterMappings.size(); i++) {
            if (param.length<1){
                throw new SQLException("该SQL命令需要参数");
            }
            if (isPrimitive){
                // 基础数据类型|其包装类型|String，默认按照顺序设置参数
                preparedStatement.setObject(i+1,param[i]);
            }else{
                String fieldName = parameterMappings.get(i).getContent();
                Field field = parameterType.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(param[0]);
                preparedStatement.setObject(i+1,fieldValue);
            }
        }
        // 5、执行sql
        int successRowCount = preparedStatement.executeUpdate();
        // 6、封装结果集
        return successRowCount;
    }


    /**
     * 获取指定类的类对象
     */
    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (!StringUtils.isNullOrEmpty(parameterType)){
            return Class.forName(parameterType,false,getClass().getClassLoader());
        }
        return null;
    }

    /**
     * 完成对#{}的解析工作：
     *      1、将#{}使用?来代替；
     *      2、解析出#{}中的值进行存储起来
     */
    private BoundSql getBoundSql(String sql) {
        // 标记处理器，将#{}中的值存储起来，并返回占位符?
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", handler);
        // 解析sql
        String parseSql = genericTokenParser.parse(sql);
        // #{}中值的集合
        List<ParameterMapping> parameterMappings = handler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql,parameterMappings);
        return boundSql;
    }
}
