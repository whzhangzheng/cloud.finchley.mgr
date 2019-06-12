package cn.zhangz.getaway2.login.handel;

import cn.zhangz.getaway2.login.model.LoginParameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录处理
 */
public interface LoginHandle {

    /**
     * 根据source决定handle
     * @param source
     * @return
     */
    boolean below(String source);

    /**
     * 将入参解析为系统可识别的用户数据
     * @param request
     * @return 登录对象
     */
    LoginParameters deal(HttpServletRequest request) throws Exception;


    /**
     * 登录页面Url
     */
    String loginPage();

    /**
     * 首页url
     */
    String index();

    /**
     * 登出处理
     * @param request
     * @param response
     */
    void logout(HttpServletRequest request, HttpServletResponse response);
}
