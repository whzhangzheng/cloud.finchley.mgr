package cn.zhangz.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping()
public class loginController {

    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 认证页面
     * @return ModelAndView
     */
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    /**
     * 清除token
     */
    @GetMapping("/removeToken")
    @ResponseBody
    public boolean removeToken(@RequestParam("access_token") String accessToken) {
        return consumerTokenServices.revokeToken(accessToken);
    }

    /**
     * 用户信息校验
     * @param authentication 信息
     * @return 用户信息
     */
    @RequestMapping("/user")
    @ResponseBody
    public Object user(Authentication authentication) {
        return authentication;
    }

}
