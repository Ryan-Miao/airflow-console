package com.ryanmiao.airflow.security.service;


/**
 * @author Ryan Miao
 * @date 2019/5/30 20:05
 */
public interface IAuthService {

    /**
     * 退出
     *
     * @param token token
     */
    void logout(String token);
}
