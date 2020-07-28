package com.ryanmiao.airflow.security.config;

import com.ryanmiao.airflow.security.utils.TokenUtils;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 负责从请求中提取token，封装令牌，传递给spring security 认证管理器去认证
 *
 * @author Ryan Miao
 * @date 2019/5/30 10:11
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public TokenAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {
        boolean debug = this.logger.isDebugEnabled();
        String token = TokenUtils.readTokenFromRequest(request);
        if (StringUtils.isBlank(token)) {
//            throw new UsernameNotFoundException("token not found");
            token = "mockToken";
        }

        if (debug) {
            this.logger.debug("Token Authentication Authorization header found ");
        }

        //token包装类, 使用principal来装载token
        UsernamePasswordAuthenticationToken tokenAuthenticationToken = new UsernamePasswordAuthenticationToken(
            token, null);

        //AuthenticationManager 负责解析
        Authentication authResult = getAuthenticationManager()
            .authenticate(tokenAuthenticationToken);
        if (debug) {
            this.logger.debug("Authentication success: " + authResult);
        }

        return authResult;
    }

    /**
     * 重写认证成功后的方法，不跳转.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(
                "Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
        getRememberMeServices().loginSuccess(request, response, authResult);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(
                new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }

        chain.doFilter(request, response);
    }
}
