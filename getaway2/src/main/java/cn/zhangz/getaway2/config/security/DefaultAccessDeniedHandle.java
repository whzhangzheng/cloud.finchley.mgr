package cn.zhangz.getaway2.config.security;

import cn.zhangz.getaway2.config.util.TokenCookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class DefaultAccessDeniedHandle implements AccessDeniedHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException
    {
        log.info("DefaultAccessDeniedHandle Authenticate fail : " + request.getSession().getAttribute("JSESSIONID"));
        log.error("Authenticate fail : " + accessDeniedException);
        response.addCookie(TokenCookieUtil.deleteTokenCookie());
        String redirectUri = "/";
        response.sendRedirect(redirectUri);
    }

}
