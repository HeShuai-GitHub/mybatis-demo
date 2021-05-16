package com.hs.orm.config;

import com.hs.orm.io.Resource;
import com.hs.orm.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author heshuai
 * @title: XmlConfigBuilder
 * @description: 解析config.xml文件，并将它的信息封装到Configuration中
 * @date 2021年05月15日 21:00
 */
public class XmlConfigBuilder {

    private Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 使用该方法通过dom4j去解析Xml文件封装Configuration实例
     */
    public Configuration parseXmlConfig(InputStream resource) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(resource);
        Element rootElement = document.getRootElement();
        List<Node> nodeList = rootElement.selectSingleNode("dataSource").selectNodes("property");
        Properties properties = new Properties();
        for (Node node : nodeList) {
            Element property = (Element)node;
            properties.setProperty(property.attributeValue("name"), property.attributeValue("value"));
        }
        /**
         * c3p0 连接池具体实现，除下面可设置连接必须的基本信息以外，还可以设置连接池初始化连接数量、最小连接数、最大连接数、闲置连接超时释放时间等。
         * c3p0属性的默认配置在这个类中有展示com.mchange.v2.c3p0.impl.C3P0Defaults
         */
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("drive"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("url"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        this.configuration.setDataSource(comboPooledDataSource);
        // 封装MapperStatement
        List<Node> mapperList = rootElement.selectSingleNode("mappers").selectNodes("mapper");
        for (Node node : mapperList) {
            Element mapper = (Element)node;
            String mapperPath = mapper.attributeValue("resource");
            InputStream mapperResource = Resource.getResourceAsStream(mapperPath);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(this.configuration);
            xmlMapperBuilder.parseXmlMapper(mapperResource);

        }
        return this.configuration;
    }

}
