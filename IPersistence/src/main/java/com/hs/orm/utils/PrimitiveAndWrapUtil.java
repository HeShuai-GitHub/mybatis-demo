package com.hs.orm.utils;

import com.hs.orm.io.Resource;
import com.mysql.cj.util.StringUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.util.Properties;

/**
 * @author heshuai
 * @title: PrimitiveAndWrapUtil
 * @description: 判断是否是基础数据类型或者其包装类|String
 * @date 2021年05月16日 11:47
 */
public class PrimitiveAndWrapUtil {


    private static Properties properties = new Properties();

    static {
        try {
            properties.load(Resource.getResourceAsStream("PWrapMapping.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 判断是否是基础数据类型的包装类型|String
     */
    public static Boolean isWrapClass(Class<?> clz){
        try {
            /**
             * boolean、byte、short、int、long、char、float、double的包装类型中都有一个TYPE的字段，该字段用来表示对应的基础数据类型，
             * 获取该字段的值get(null)，即可以获取到对应的基本类型的值，将它强转成Class对象，就可以通过isPrimitive()来判断是否是基础数据类型了
             */
            return clz.getTypeName().equals("java.lang.String") || ((Class)clz.getField("TYPE").get(null)).isPrimitive();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 判断是否是基础数据类型或其包装类型或String类型
     */
    public static Boolean isPrimitiveAndWrapClass(String className) throws ClassNotFoundException {
        if (StringUtils.isNullOrEmpty(className)){
            return false;
        }
        // 基础数据类型映射的包装类型
        String classMapping = properties.getProperty(className);
        if (StringUtils.isNullOrEmpty(classMapping)){
            return isWrapClass(Class.forName(className));
        }
        return isWrapClass(Class.forName(classMapping));
    }

}
