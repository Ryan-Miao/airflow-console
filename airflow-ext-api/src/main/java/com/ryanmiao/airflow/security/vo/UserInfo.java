package com.ryanmiao.airflow.security.vo;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

/**
 * @author Ryan Miao
 * @date 2020/7/28 14:07
 */
@Data
public class UserInfo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 角色
     */
    private Set<String> roles;
    /**
     * 权限
     */
    private Set<SimpleGrantedAuthority> authorities;

    private String token;
}
