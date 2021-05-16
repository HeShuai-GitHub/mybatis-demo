package com.hs.proxy.jdk.dynamic;

import com.hs.proxy.jdk.Hello;
import com.hs.proxy.jdk.HelloImpl;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author heshuai
 * @title: HelloProxy
 * @description: 动态代理类
 * @date 2021年05月12日 15:29
 */
public class HelloProxy {

    private HelloImpl hello = new HelloImpl();

    @Test
    public void testProxy(){
        /**
         * newProxyInstance 根据指定的参数动态创建代理对象
         * loader：代理对象的类加载器
         * interfaces：代理对象需要实现的接口列表
         * h：调用处理器接口
         */
        Hello o = (Hello)Proxy.newProxyInstance(Hello.class.getClassLoader(), new Class<?>[]{Hello.class}, new InvocationHandler() {
            /**
             * 核心方法，集中处理所有动态代理类上的所有方法的调用
             * @param proxy 代理对象实例
             * @param method 被调用的方法
             * @param args 调用参数
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("动态代理：");
                return method.invoke(hello, args);
            }
        });
        System.out.println(o.sayHello());
    }
}
