package com.xiaoyetan.jfinal;

import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.xiaoyetan.Const;
import com.xiaoyetan.common.mq.plugins.ActivemqPlugin;

/**
 * @Author xiaoyetan
 * @Date :created on 17:22 2017/8/23
 */
public class JfinalCoreConfig extends JFinalConfig {
    //配置常量
    public void configConstant(Constants me) {
        PropKit.use("config.properties");
    }

    //配置路由
    public void configRoute(Routes me) {
    }

    //配置插件
    public void configPlugin(Plugins me) {
        ActivemqPlugin mqPlugin = new ActivemqPlugin(Const.getValue("mq.brokerURL"));
        me.add(mqPlugin);
    }

    //配置拦截器
    public void configInterceptor(Interceptors me) {

    }

    //配置处理器
    public void configHandler(Handlers me) {
    }

}
