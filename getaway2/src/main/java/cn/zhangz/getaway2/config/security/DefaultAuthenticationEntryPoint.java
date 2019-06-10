package cn.zhangz.getaway2.config.security;

import cn.zhangz.getaway2.config.util.TokenCookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException
    {
        log.error("Authenticate fail : " + request.getSession().getAttribute("token"));
        log.error("Authenticate fail : " + authException);
        response.addCookie(TokenCookieUtil.deleteTokenCookie());
        String redirectUri = "/";
        response.sendRedirect(redirectUri);
    }
}
