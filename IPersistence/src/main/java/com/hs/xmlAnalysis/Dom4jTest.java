package com.hs.xmlAnalysis;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @title: Dom4jTest
 * @description: Dom4j和Xpath 常用API测试
 * @date 2021年05月10日 20:20
 */
public class Dom4jTest {

    @Test
    public void testDom4j() throws DocumentException {
        // 通过类加载器来加载文件到输入流中
        InputStream resource = Dom4jTest.class.getClassLoader().getResourceAsStream("dom4jTest.xml");
        // 读取输入流
        Document document = new SAXReader().read(resource);
        // 获取文档节点
        Element root = document.getRootElement();
        // 获取标签名
        System.out.println(root.getName());
        // Node中可用于元素、属性、文本等
        // 通过Xpath表达式获取root根节点下所有子元素中name属性对象
        List<Node> name = root.selectNodes("//@name");
        for (Node node : name) {
            System.out.println(node.getStringValue());
        }
        // 获取单个node对象
        Node testText = root.selectSingleNode("//testText");
        // 获取标签中文本
        System.out.println(testText.getText());
        // 获取绝对路径configuration后代节点中property元素
        List<Node> property = root.selectNodes("/configuration//property");
        for (Node node : property) {
            // @name这个是相对路径下name属性
            System.out.println(node.selectSingleNode("@name").getName()+"："+node.selectSingleNode("attribute::name").getStringValue());
        }
        // 通过强转成Element来获取元素的值
        for (Node node : property) {
            Element element = (Element)node;
            System.out.println(element.attribute("name").getName()+"："+element.attributeValue("name"));
        }
    }
}
