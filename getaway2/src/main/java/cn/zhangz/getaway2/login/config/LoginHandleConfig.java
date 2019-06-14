package cn.zhangz.getaway2.login.config;

import cn.zhangz.getaway2.login.handel.BjcLoginHandle;
import cn.zhangz.getaway2.login.handel.DefaultLoginHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginHandleConfig {
    @Autowired
    private LoginProperties loginProperties;

    private LoginIndex getLoginIndex(){
        return loginProperties;
    }

    @Bean
    public LoginHandleActuator loginHandleActuator(){
        LoginHandleActuator loginHandleActuator = new LoginHandleActuator();
        //创建LoginHandle容器
        LoginHandleContainer loginHandleContainer = new DefaultLoginHandleContainer();

        //支持默认登录
        DefaultLoginHandle defaultLoginHandle = new DefaultLoginHandle();
        defaultLoginHandle.setIndexUrl(getLoginIndex().getLoginIndex());
        loginHandleContainer.addLoginHandle(defaultLoginHandle);

        //支持bjc登录
        BjcLoginHandle bjcLoginHandle = new BjcLoginHandle();
        bjcLoginHandle.setIndexUrl(getLoginIndex().getLoginIndex());
        loginHandleContainer.addLoginHandle(bjcLoginHandle);


        loginHandleActuator.setLoginHandleContainer(loginHandleContainer);
        return loginHandleActuator;
    }

}
