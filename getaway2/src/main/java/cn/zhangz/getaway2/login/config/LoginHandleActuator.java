package cn.zhangz.getaway2.login.config;

import cn.zhangz.getaway2.login.handel.LoginHandle;
import cn.zhangz.getaway2.login.model.LoginParameters;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@NoArgsConstructor
public class LoginHandleActuator {

    private LoginHandleContainer loginHandleContainer = new DefaultLoginHandleContainer();

    public LoginHandleContainer getLoginHandleContainer() {
        return loginHandleContainer;
    }

    public void setLoginHandleContainer(LoginHandleContainer loginHandleContainer) {
        this.loginHandleContainer = loginHandleContainer;
    }

    public void addLoginHandle(LoginHandle loginHandle){
        getLoginHandleContainer().addLoginHandle(loginHandle);
    }

    /**
     * 获取首页
     * @param loginModel 登录model
     * @return
     */
    public String getIndex(String loginModel) throws Exception
    {
        LoginHandle handle = getLoginHandleContainer().getHandle(loginModel);
        if(null == handle){
            throw new Exception("UnSupport Login Model!");
        }
        return handle.index();
    }

    /**
     * 获取登录页面
     * @param loginModel 登录model
     * @return
     */
    public String getLoginPage(String loginModel) throws Exception
    {
        LoginHandle handle = getLoginHandleContainer().getHandle(loginModel);
        if(null == handle){
            throw new Exception("UnSupport Login Model!");
        }
        return handle.loginPage();
    }

    /**
     * 处理登录请求
     * @param loginModel 登录model
     * @param request 登录请求
     * @return
     */
    public LoginParameters handle(String loginModel, HttpServletRequest request) throws Exception
    {
        LoginHandle handle = getLoginHandleContainer().getHandle(loginModel);
        if(null == handle){
            throw new Exception("UnSupport Login Model!");
        }
        return handle.deal(request);
    }

    /**
     * 登出
     * @param loginModel 登录model
     * @param request
     * @param response
     */
    public void logout(String loginModel, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoginHandle handle = getLoginHandleContainer().getHandle(loginModel);
        if(null == handle){
            throw new Exception("UnSupport Login Model!");
        }
        handle.logout(request,response);
    }
}
