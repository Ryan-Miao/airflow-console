package com.ryanmiao.airflow.security.config;

import com.ryanmiao.airflow.security.vo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 这里只使用了username字段。
 *
 * @author Ryan Miao
 * @date 2019/5/29 22:05
 */
public class UserTokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserClient userClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenAuthenticationProvider.class);

    public UserTokenAuthenticationProvider(UserClient userClient) {
        this.userClient = userClient;
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {

    }


    @Override
    protected UserDetails retrieveUser(String token,
                                       UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {
        //验证token --- from remote client
        try {
            UserInfo data = userClient.getCurrentUser(token);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("currentUser:" + data);
            }

            authentication.setDetails(data);
            return new User(data.getUsername(), "password", data.getAuthorities());
        } catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("get current user fail:" + ex.getMessage());
            }
            throw new CredentialsExpiredException(ex.getMessage(), ex);
        }
    }

    /**
     * 对应我们的Token令牌类UsernamePasswordAuthenticationToken，可以采用本provide验证.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class
            .isAssignableFrom(authentication));
    }
}
