package com.hs.orm.config;

import com.hs.orm.pojo.Configuration;
import com.hs.orm.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;

/**
 * @author heshuai
 * @title: XmlMapperBuilder
 * @description: 解析Mapper.xml文件，并将它封装到MappedStatement中
 * @date 2021年05月15日 21:39
 */
public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseXmlMapper(InputStream mapperResource) throws DocumentException {
        Document document = new SAXReader().read(mapperResource);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        Iterator<Element> elementIterator = rootElement.elementIterator();
        while (elementIterator.hasNext()) {
            Element element = elementIterator.next();
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String name = element.getName();
            String sql = element.getTextTrim();
            MappedStatement mapper = new MappedStatement();
            mapper.setId(id);
            mapper.setParameterType(parameterType);
            mapper.setSql(sql);
            mapper.setResultType(resultType);
            mapper.setStatementType(name);
            // statementId = namespace.id
            String key = namespace+"."+id;
            this.configuration.getStatementMap().put(key,mapper);
        }
    }
}
