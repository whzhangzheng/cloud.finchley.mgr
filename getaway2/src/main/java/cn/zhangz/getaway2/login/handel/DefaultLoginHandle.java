package cn.zhangz.getaway2.login.handel;

import cn.zhangz.getaway2.login.constants.LoginConstants;
import cn.zhangz.getaway2.login.model.LoginParameters;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultLoginHandle implements LoginHandle {

    private final String defaultSource = LoginConstants.default_login_model;

    private String indexUrl;

    public void setIndexUrl(String indexUrl){
        this.indexUrl = indexUrl;
    }

    public String getIndexUrl(){
        return this.indexUrl;
    }

    @Override
    public boolean below(String source) {
        if(!StringUtils.isEmpty(source) && defaultSource.equalsIgnoreCase(source)){
            return true;
        }
        return false;
    }

    @Override
    public LoginParameters deal(HttpServletRequest request) throws Exception{
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        if(StringUtils.isEmpty(userName)
                || StringUtils.isEmpty(password)){
            throw new Exception("user or password is null");
        }
        return new LoginParameters(userName,password);
    }

    @Override
    public String loginPage() {
        return "/login";
    }

    @Override
    public String index() {
        return getIndexUrl();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(loginPage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
