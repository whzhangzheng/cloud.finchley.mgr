package cn.zhangz.getaway2.login.controller;


import cn.zhangz.getaway2.login.config.LoginModel;
import cn.zhangz.getaway2.login.config.LoginProperties;
import cn.zhangz.getaway2.login.config.LoginHandleActuator;
import cn.zhangz.getaway2.login.constants.LoginConstants;
import cn.zhangz.getaway2.login.model.LoginParameters;
import cn.zhangz.getaway2.login.model.PasswordTokenParameters;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
@RequestMapping()
public class LoginController extends AbstractLoginController {

    @Autowired
    private LoginHandleActuator loginHandleActuator;

    @Autowired
    private LoginProperties loginProperties;

    private LoginModel getModel(){
        return loginProperties;
    }

    private String getLoginModel(){
        if(null == getModel() || StringUtils.isEmpty(getModel().getLoginModel())){
            return LoginConstants.default_login_model;
        }
        return getModel().getLoginModel();
    }

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("appName", "");
    }

    @GetMapping({"/",""})
    public void loginPage(HttpServletRequest request, HttpServletResponse response){
        String redirect = null;
        try{
            redirect = loginHandleActuator.getLoginPage(getLoginModel());
        }catch (Exception e){
            log.error("Get Login Page Error! " + e.getMessage());
            redirect = "/login";
        }

        try {
            response.sendRedirect(redirect);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch(IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认登录页面
     * @param model
     * @param error
     * @return
     */
    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value="error", required=false) String error){
        model.addAttribute("msg",error);
        return "login";
    }

    /**
     * 无权限页面
     * @return
     */
    @GetMapping("/forbid")
    public String forbid(){
        return "forbid";
    }


    /**
     * 登录
     * @param model 登录model
     * @param request
     * @param response
     */
    @RequestMapping("/doLogin/{model}")
    @ResponseBody
    public void doLogin(@PathVariable(value = "model") String model, HttpServletRequest request, HttpServletResponse response){
        LoginParameters loginParameters = null;
        try{
            //设置首页
            setIndex(loginHandleActuator.getIndex(model));
            //设置登录地址
            setLoginUrl(loginHandleActuator.getLoginPage(model));
            //获取登录信息
            loginParameters = loginHandleActuator.getLoginInfo(model,request);
        }catch (Exception e){
            exceptionHandle(request,response,new Exception("user or password is null"));
            return;
        }
        if(null == loginParameters){
            exceptionHandle(request,response,new Exception("UnSupport Login Model!"));
            return;
        }
        PasswordTokenParameters parameters = getTokenParameter(loginParameters.getUserName(),loginParameters.getPassword());
        super.doLogin(request,response,parameters);
    }


}
