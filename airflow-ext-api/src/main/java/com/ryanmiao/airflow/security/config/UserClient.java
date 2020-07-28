package com.ryanmiao.airflow.security.config;

import com.google.common.collect.Sets;
import com.ryanmiao.airflow.security.vo.UserInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 本class代理登录相关信息获取, 可以远程获取.
 * 当前写死固定, 使用者可以接入自己的认证系统.
 *
 * @author Ryan Miao
 * @date 2020/7/28 14:07
 */
public class UserClient {

    /**
     * 根据token获取当前登录用户.
     *
     * @param token token
     * @return userInfo
     */
    public UserInfo getCurrentUser(String token) {
        UserInfo info = new UserInfo();
        info.setUsername("ryan.miao");
        Set<String> roles = Sets.newHashSet("admin", "dev", "analysis");
        info.setRoles(roles);
        info.setToken(token);
        info.setEmail("ryan.miao@demo.com");
        Set<SimpleGrantedAuthority> authorities = roles.stream()
            .flatMap(r -> this.mapAuthoritiesByRole(r).stream())
            .collect(Collectors.toSet());
        info.setAuthorities(authorities);
        return info;
    }

    private List<SimpleGrantedAuthority> mapAuthoritiesByRole(String role) {
        return new ArrayList<>();
    }

    public void logOut(String token) {
        //do something to remove token from cache.
    }
}
