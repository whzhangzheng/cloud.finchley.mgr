package cn.zhangz.getaway2.login.model;

import cn.zhangz.getaway2.config.constants.Oauth2Constants;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class PasswordTokenParameters {

    private String userName;
    private String password;
    private String grantType = Oauth2Constants.oauth2_password;
    private String scope = Oauth2Constants.root_scope;
    private String clientId;
    private String clientSecret;

    public MultiValueMap<String, Object> extractTokenParameters(){
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add(Oauth2Constants.oauth2_username,this.userName);
        postParameters.add(Oauth2Constants.oauth2_password,this.password);
        postParameters.add(Oauth2Constants.oauth2_scope,this.scope);
        postParameters.add(Oauth2Constants.oauth2_clint_id,this.clientId);
        postParameters.add(Oauth2Constants.oauth2_clint_secret,this.clientSecret);
        postParameters.add(Oauth2Constants.oauth2_grant_type,this.grantType);
        return postParameters;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
