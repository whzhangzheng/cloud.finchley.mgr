package cn.zhangz.getaway2.config.security;


import cn.zhangz.getaway2.config.constants.Oauth2Constants;
import cn.zhangz.getaway2.config.constants.SecurityConstants;
import cn.zhangz.getaway2.config.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableResourceServer
public class ResourceConfigurer extends ResourceServerConfigurerAdapter {

    @Autowired
    private AccessTokenProperties accessTokenProperties;
    @Autowired
    private FilterIgnoreProperties filterIgnoreProperties;
    @Autowired
    private RestTemplate restTemplate;

    private AccessDeniedHandler accessDeniedHandler = new DefaultAccessDeniedHandle();

    private AuthenticationEntryPoint authenticationEntryPoint = new DefaultAuthenticationEntryPoint();

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(
                SecurityConstants.http_prefix + accessTokenProperties.getAppName() + Oauth2Constants.check_token_url);
        remoteTokenServices.setClientId(accessTokenProperties.getClientId());
        remoteTokenServices.setClientSecret(accessTokenProperties.getSecret());
        remoteTokenServices.setRestTemplate( restTemplate );
        return remoteTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenServices()).accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套
        //开启缓存设置
        http.headers().frameOptions().disable().cacheControl().disable();
        http.addFilterAfter(new TokenFilter(), LogoutFilter.class);
        http.logout().disable();
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        filterIgnoreProperties.getUrls().forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated();
    }

}
