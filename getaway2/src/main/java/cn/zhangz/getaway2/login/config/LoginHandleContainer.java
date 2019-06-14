package cn.zhangz.getaway2.login.config;

import cn.zhangz.getaway2.login.handel.LoginHandle;

public interface LoginHandleContainer {
    /**
     * 添加handle
     * @param loginHandle
     */
    void addLoginHandle(LoginHandle loginHandle);

    /**
     * 匹配对应的handle
     * @param loginModel 登录model
     */
    LoginHandle getHandle(String loginModel);
}
