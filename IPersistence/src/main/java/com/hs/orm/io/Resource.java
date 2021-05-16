package com.hs.orm.io;

import java.io.InputStream;

/**
 * @author heshuai
 * @title: Resource
 * @description: 文件加载配置文件到内存中
 * @date 2021年05月14日 22:26
 */
public class Resource {

    // 根据配置文件的路径，将配置文件加载成字节输入流，存储在内存中
    public static InputStream getResourceAsStream(String path){
        return Resource.class.getClassLoader().getResourceAsStream(path);
    }


}
