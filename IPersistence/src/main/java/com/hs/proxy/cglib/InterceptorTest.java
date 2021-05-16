package com.hs.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author heshuai
 * @title: InterceptorTest
 * @description: cglib:Code Generate Library，是一个基于ASM的字节码生成库，运行我们在运行时对字节码进行修改和动态生成
 * @date 2021年05月12日 16:02
 */
public class InterceptorTest {
    @Test
    public void InterceptTest(){
        /**
         * 动态生成一个被代理类的子类，以拦截被代理类的方法
         * jdk1.3将包含cglib动态代理，动态代理生成子类覆盖父类非final方法，并实现MethodInterceptor方法以达到自定义拦截方法
         */
        Enhancer enhancer = new Enhancer();
        // 设置被代理类，注：必须被代理类必须有一个构造器
        enhancer.setSuperclass(Hello.class);
        // 设置代理拦截器
        enhancer.setCallback(new MethodInterceptor() {
            // 拦截方法
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("动态代理：");
                // 执行被代理方法
                return methodProxy.invokeSuper(o, objects);
            }
        });
        // create() 生成一个新的代理class，使用父类的无参构造器，如果是只有有参构造器，那么必须使用有参create()方法
        Hello hello = (Hello)enhancer.create();
        System.out.println(hello.say());
    }
}
