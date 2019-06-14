package cn.zhangz.getaway2.login.handel;

import cn.zhangz.getaway2.login.constants.LoginConstants;
import cn.zhangz.getaway2.login.model.LoginParameters;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
public class BjcLoginHandle implements LoginHandle {

    private final String bjcSource = LoginConstants.bjc_login_model;

    private String indexUrl;

    public void setIndexUrl(String indexUrl){
        this.indexUrl = indexUrl;
    }

    public String getIndexUrl(){
        return this.indexUrl;
    }

    @Override
    public boolean below(String source) {
        if(bjcSource.equalsIgnoreCase(source)){
            return true;
        }
        return false;
    }

    @Override
    public LoginParameters deal(HttpServletRequest request) throws Exception {
        String bjcToken = request.getParameter("SECURE_GLOBAL_TOKEN");
        log.debug("----------- OA Login Token : " + bjcToken);

        String user = getUserInfo(bjcToken);

        LoginParameters loginParameters = new LoginParameters("admin","admin");
        return loginParameters;
    }

    @Override
    public String loginPage() {
        return "http://test.sso.billjc.com/Secure/Authenticate?SECURE_CLIENT_APPID=HOST&SECURE_RETURN_URL=http://localhost:80/doLogin/bjc";
    }

    @Override
    public String index() {
        return getIndexUrl();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("http://test.sso.billjc.com/secure/signout?SECURE_CLIENT_APPID=HOST&SECURE_RETURN_URL=http://localhost:80/doLogin/bjc");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过OA的Token获取用户信息,若系统不存在该用户,则创建该用户,成功后登录;若系统存在该用户,则用该用户登录;
     * 任何调用失败返回null,上层处理返回逻辑
     * @param bjcToken
     * @return
     */
    private String getUserInfo(String bjcToken) {

        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("ContextID", String.valueOf(new Date().getTime()));
        postParameters.add("Data", bjcToken);
        String result = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.postForObject("http://test.sso.billjc.com/Secure/GetIdentity", postParameters, String.class);
        } catch (HttpClientErrorException e) {
            log.error("BjcLoginController getUserInfo HttpClientErrorException : " + e);
            return null;
        }
        log.debug("BjcLoginController getUserInfo result : " + result);
        Map data = (Map) ((JSONObject) JSONObject.fromObject(result).get("Data")).get("Identity");
        if (null == data || StringUtils.isEmpty(data.get("GlobalID").toString())) {
            return null;
        }
        String workId = data.get("GlobalID").toString();
        log.info("------------workId-------------" + workId);
        return workId;
    }
}
