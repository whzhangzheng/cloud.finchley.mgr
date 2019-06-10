package cn.zhangz.getaway2.config.filter;

import cn.zhangz.getaway2.config.model.MutableHttpServletRequest;
import cn.zhangz.getaway2.config.util.TokenCookieUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class TokenFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        MutableHttpServletRequest request1 = null;
        if( request instanceof HttpServletRequest){
            request1 = new MutableHttpServletRequest((HttpServletRequest) request);
            //Object token = ((HttpServletRequest) request).getSession().getAttribute("token");
            String token = null;
            Cookie[] cookies = ((HttpServletRequest) request).getCookies();
            if(null == cookies || 0 == cookies.length){
                chain.doFilter(request , response);
                return;
            }
            token = TokenCookieUtil.getTokenFromCookie(cookies);
            if(null != token){
                request1.putHeader("Authorization","Bearer " + token);
            }
        }
        chain.doFilter(null == request1?request:request1 , response);
    }


    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

}
