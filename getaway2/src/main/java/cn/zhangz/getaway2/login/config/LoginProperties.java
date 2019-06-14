package cn.zhangz.getaway2.login.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${sys.login}'.isEmpty()")
@ConfigurationProperties(prefix = "sys.login")
public class LoginProperties implements LoginIndex, LoginModel {
    private String model;
    private String index;

    @Override
    public String getLoginIndex() {
        return this.index;
    }

    @Override
    public String getLoginModel() {
        return this.model;
    }
}
