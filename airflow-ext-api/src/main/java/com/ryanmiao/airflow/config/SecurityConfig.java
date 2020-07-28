package com.ryanmiao.airflow.config;

import com.ryanmiao.airflow.security.config.TokenAuthenticationFilter;
import com.ryanmiao.airflow.security.config.UserClient;
import com.ryanmiao.airflow.security.config.UserTokenAuthenticationProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;

/**
 * @author Ryan Miao
 * @date 2019/5/29 19:54
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(
            new UserTokenAuthenticationProvider(userClient()));

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] ignoreUrl = {"/v1/auth/**", "/", "/error", "/swagger-*/**", "/favicon.ico",
            "/webjars/**"};
        web.ignoring().antMatchers(
            ignoreUrl
        );

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter("/**");
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler());
        //如果本filter认证失败，是否继续认证，如果还有其他认证方式可以继续，
        //本demo只有token认证一种方案，认证失败即返回
        filter.setContinueChainBeforeSuccessfulAuthentication(false);

        //CHECKSTYLE:OFF
        http.cors()
            .and()
            .headers().frameOptions().disable()
            .and().csrf().disable()
            .authorizeRequests()
            //允许以下请求
//            .antMatchers("/v1/auth/**").permitAll()
            // 所有请求需要身份认证
            .anyRequest().authenticated()
            .and()
            //login   username,password通过UsernamePasswordAuthenticationFilter实现装载
            //token认证   需要对应的token解析，位于UsernamePasswordAuthenticationFilter之后
            .addFilterBefore(filter,
                BasicAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(new DigestAuthenticationEntryPoint())
            .accessDeniedHandler(new AccessDeniedHandlerImpl())
        ;
        //CHECKSTYLE:ON
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 移除权限的默认前缀ROLE_
     *
     * @return GrantedAuthorityDefaults
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    @ConfigurationProperties(prefix = "auth")
    public AuthProperties authProperties() {
        return new AuthProperties();
    }

    /**
     * 认证失败后跳转
     *
     * @return 认证失败后跳转
     */
    private SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler(
            "/v1/auth/error");
        handler.setUseForward(true);
        return handler;
    }

    @Bean
    public UserClient userClient() {
        return new UserClient();
    }
}
