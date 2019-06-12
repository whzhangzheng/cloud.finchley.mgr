package cn.zhangz.getaway2.login.config;

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
    public LoginHandleAdepter loginHandleAdepter(){
        LoginHandleAdepter loginHandleAdepter = new LoginHandleAdepter();
        //每多支持一种来源的登录,都需要在此处添加一个handle
        DefaultLoginHandle defaultLoginHandle = new DefaultLoginHandle();
        defaultLoginHandle.setIndexUrl(getLoginIndex().getLoginIndex());
        loginHandleAdepter.addLoginHandle(defaultLoginHandle);

        return loginHandleAdepter;
    }

}
