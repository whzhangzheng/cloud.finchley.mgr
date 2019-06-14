package cn.zhangz.getaway2.login.config;

import cn.zhangz.getaway2.login.handel.LoginHandle;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class DefaultLoginHandleContainer implements LoginHandleContainer {

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
    public LoginHandle getHandle(String loginModel)
    {
        if(CollectionUtils.isEmpty(getHandles())){
            return null;
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
}
