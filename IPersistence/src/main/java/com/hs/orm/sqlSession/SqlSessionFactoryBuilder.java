package com.hs.orm.sqlSession;

import com.hs.orm.config.XmlConfigBuilder;
import com.hs.orm.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author heshuai
 * @title: SqlSessionFactoryBuilder
 * @description: SqlSessionFactory构建类，通过该类创建SqlSessionFactory接口的实现类实例，封装Configuration配置信息
 * @date 2021年05月15日 20:53
 */
public class SqlSessionFactoryBuilder {

    /**
     * 构建者模式，将复杂对象的构造与其表示分离，以便相同的构造过程可以创建不同的表示
     * 这里是构建DefaultSqlSessionFactory实例
     */
    public SqlSessionFactory build(InputStream resource) throws PropertyVetoException, DocumentException {
        // 使用dom4j解析xml配置文件，将配置信息封装到Configuration中
        XmlConfigBuilder xmlCOnfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlCOnfigBuilder.parseXmlConfig(resource);
        // 创建sqlSessionFactory对象
        return new DefaultSqlSessionFactory(configuration);
    }

}
