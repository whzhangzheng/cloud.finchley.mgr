package cn.zhangz.getaway2.login.controller;

import cn.zhangz.getaway2.config.constants.Oauth2Constants;
import cn.zhangz.getaway2.config.constants.SecurityConstants;
import cn.zhangz.getaway2.config.security.AccessTokenProperties;
import cn.zhangz.getaway2.config.util.TokenCookieUtil;
import cn.zhangz.getaway2.exception.GetTokenException;
import cn.zhangz.getaway2.login.model.PasswordTokenParameters;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public abstract class AbstractLoginController {
    @Autowired
    private AccessTokenProperties accessTokenProperties;

    public AccessTokenProperties getAccessTokenProperties(){
        return this.accessTokenProperties;
    }

    /**
     *  系统首页
     */
    private String index;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index){
        this.index = index;
    }

    /**
     * 登录页面
     */
    private String loginUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl){
        this.loginUrl = loginUrl;
    }

    @Autowired
    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate(){
        return restTemplate;
    }

    /**
     * 实现登录(获取token,跳转首页)
     * @param request
     * @param response
     * @param parameters
     */
    protected final void doLogin(HttpServletRequest request, HttpServletResponse response, PasswordTokenParameters parameters){
        String token;
        try {
            checkParameters(parameters);
            token = getToken(parameters);
            redirectToIndex(request, response, token);
        }catch (Exception e)
        {
            exceptionHandle(request,response,e);
            return;
        }
    }

    /**
     * 跳转首页,特殊需求可重写,跳转至自定义页面
     * @param request
     * @param response
     * @param token
     * @throws IOException
     */
    public void redirectToIndex(HttpServletRequest request, HttpServletResponse response, String token) throws IOException {
        //request.getSession().setAttribute("token",token);
        setTokenCookie(response,token);
        response.sendRedirect(getIndex());
    }

    /**
     * 实现获取token逻辑
     * @param parameters
     * @return
     * @throws GetTokenException
     */
    private String getToken(PasswordTokenParameters parameters) throws GetTokenException {
        String data;
        boolean successFlag = true;
        try {
            data = getRestTemplate().postForObject(
                    SecurityConstants.http_prefix + getAccessTokenProperties().getAppName() + Oauth2Constants.get_token_url,
                    parameters.extractTokenParameters() , String.class);
        }catch (HttpClientErrorException e){
            log.error(e.toString());
            data = "HttpClientErrorException:" + e;
            successFlag = false;
        }
        log.info(data);
        Map<String,String> tokenResult;
        try {
            tokenResult = JSONObject.fromObject(data);
        }catch (Exception e){
            throw new GetTokenException(data);
        }
        if( successFlag && null != tokenResult && !StringUtils.isEmpty(tokenResult.get(Oauth2Constants.oauth2_token_result_key)) ){
            return tokenResult.get(Oauth2Constants.oauth2_token_result_key);
        }
        throw new GetTokenException(tokenResult.get(Oauth2Constants.oauth2_token_result_error_key));
    }

    /**
     * 验证获取token的参数正确性
     * @param parameters
     * @throws Exception
     */
    private void checkParameters(PasswordTokenParameters parameters) throws Exception{
        if(StringUtils.isEmpty(parameters.getUserName())
                || StringUtils.isEmpty(parameters.getPassword())
                || StringUtils.isEmpty(parameters.getClientId())
                || StringUtils.isEmpty(parameters.getClientSecret())
                || StringUtils.isEmpty(parameters.getScope())){
            throw new Exception("parameters error! UserName or Password or clientId or clientSecret or scope is null!");
        }
        return;
    }

    /**
     * 登录异常处理
     * @param request
     * @param response
     * @param e
     */
    public void exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception e){
        log.error("----------------Login Exception-----------------" + e );
        try {
            response.sendRedirect(getLoginUrl()+"?error="+e.getMessage());
        }catch (IOException ioe){
            log.error("Login fail! Redirect to Login Exception : " + ioe);
        }
    }

    private void setTokenCookie(HttpServletResponse response, String value) {
        response.addCookie(TokenCookieUtil.createTokenCookie(value));
    }

    /**
     * 构造获取token的参数对象
     * @param userName
     * @param password
     * @return
     */
    public PasswordTokenParameters getTokenParameter(String userName, String password){
        PasswordTokenParameters parameters = new PasswordTokenParameters();
        parameters.setUserName(userName);
        parameters.setPassword(password);
        parameters.setClientId(getAccessTokenProperties().getClientId());
        parameters.setClientSecret(getAccessTokenProperties().getSecret());
        return parameters;
    }
}
