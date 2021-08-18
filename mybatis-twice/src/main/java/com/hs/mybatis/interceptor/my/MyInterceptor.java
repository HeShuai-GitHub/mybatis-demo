package com.hs.mybatis.interceptor.my;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @author heshuai
 * @title: MyInterceptor
 * @description: 自定义插件
 * @date 2021年08月17日 17:32
 */
@Intercepts({ // 可定义多个Signature，同时对不同类型进行拦截
        @Signature(
                type = Executor.class, // 所拦截的接口
                method = "query",  // 所拦截的方法
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class} // 为防止重载，将方法的形参展示到这里
        )
})
public class MyInterceptor implements Interceptor {

    private String value;

    /**
     * 类似于动态代理中具体的实现逻辑，在目标方法执行前和执行后添加一些增强逻辑
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("在方法执行前进行了加强处理....");
        System.out.println("属性是：" + this.value);
        Object proceed = invocation.proceed();
        System.out.println("在方法执行后进行了加强处理");
        return proceed;
    }

    /**
     * 主要是为了把这个拦截器生成一个代理放到拦截器链中
     * @param target 代理对象
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**获取配置文件的属性**/
    //插件初始化的时候调用，也只调用一次，插件配置的属性从这里设置进来
    @Override
    public void setProperties(Properties properties) {
        this.value = properties.getProperty("value");
    }
}
