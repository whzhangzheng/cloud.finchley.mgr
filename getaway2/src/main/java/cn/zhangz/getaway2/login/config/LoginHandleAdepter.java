package cn.zhangz.getaway2.login.config;

import cn.zhangz.getaway2.login.handel.LoginHandle;
import cn.zhangz.getaway2.login.model.LoginParameters;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class LoginHandleAdepter {

    private List<LoginHandle> handles;

    public List<LoginHandle> getHandles(){
        return this.handles;
    }

    public void addLoginHandle(LoginHandle loginHandle){
        if(null == loginHandle){
            return;
        }
        if(CollectionUtils.isEmpty(this.handles)){
            this.handles = new ArrayList<>();
        }
        this.handles.add(loginHandle);
    }

    /**
     * 匹配对应的handle
     * @param loginModel 登录model
     */
    private LoginHandle getHandle(String loginModel) throws Exception
    {
        if(CollectionUtils.isEmpty(getHandles())){
            throw new Exception("未配置登录处理handle类!");
        }
        LoginHandle handle = null;
        for (LoginHandle tmpHandle : getHandles()){
            if(tmpHandle.below(loginModel)){
                handle = tmpHandle;
                break;
            }
        }
        return handle;
    }

    /**
     * 获取首页
     * @param loginModel 登录model
     * @return
     */
    public String getIndex(String loginModel) throws Exception
    {
        LoginHandle handle = getHandle(loginModel);
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
        LoginHandle handle = getHandle(loginModel);
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
        LoginHandle handle = getHandle(loginModel);
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
        LoginHandle handle = getHandle(loginModel);
        if(null == handle){
            throw new Exception("UnSupport Login Model!");
        }
        handle.logout(request,response);
    }
}
