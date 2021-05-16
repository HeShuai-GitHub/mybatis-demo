package com.hs.orm.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author heshuai
 * @title: Configuration
 * @description: 配置信息
 * @date 2021年05月14日 22:35
 */
@Data
public class Configuration {
    /**
     *  数据源 java对外提供的数据源接口，它是一个连接池，可以用来创建数据库连接，这个只是一个java对外提供的接口协议，具体实现由数据库厂商来实现
     *  另外一个可以获取数据库连接的方式是DriverManager，但是DataSource相对于DriverManager是更好的一种方式。
     *  通常有三种实现方式：
     *      1、基础实现方式：可以生成一个标准的数据库连接；
     *      2、连接池实现：生成一个数据库连接并加入到连接池中，这种实现方式由中间连接池来进行管理；
     *      3、分布式事务实现：生成一个用于分布式事务的数据库连接并加入到连接池中进行管理，这种工作方式由中间分布式事务和线程池管理器管理。
     *  注：通过DriverManager和DataSource获取连接，最后的数据库连接是一样的，只是产生和实现的方式不一样。
     */
    private DataSource dataSource;
    // key:statementId           value:statement
    private Map<String,MappedStatement> statementMap = new HashMap<>();

    public MappedStatement getStatementByMap(String key){
        return this.statementMap.get(key);
    }

    public void setStatementIntoMap(String key, MappedStatement value){
        this.statementMap.put(key,value);
    }
}
