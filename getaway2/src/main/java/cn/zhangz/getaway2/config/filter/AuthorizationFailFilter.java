package cn.zhangz.getaway2.config.filter;

import cn.zhangz.common.model.Result;
import cn.zhangz.getaway2.config.util.HttpUtil;
import cn.zhangz.getaway2.config.util.TokenCookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class AuthorizationFailFilter extends ZuulFilter {

    @Override
    public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        switch ( response.getStatus() ){
            case 401:
                TokenCookieUtil.deleteTokenCookie();
                try {
                    response.sendRedirect("/");
                } catch (IOException e) {
                    log.error("Redirect Fail!" + e.getMessage());
                }
                break;
            case 403:
                if( HttpUtil.isAjaxRequest(request) ){
                    try(PrintWriter out = response.getWriter()) {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json;charset=utf-8");
                        out.append(JSONObject.fromObject(new Result().error(403,"no security")).toString());
                    }catch (IOException e){
                        log.error("Ajax Redirect Fail!" + e.getMessage());
                        e.printStackTrace();
                    }
                }else{
                    try {
                        response.sendRedirect("/forbid");
                    }catch (IOException e){
                        log.error("Redirect Fail!" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                break;
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return "post";
    }

}
