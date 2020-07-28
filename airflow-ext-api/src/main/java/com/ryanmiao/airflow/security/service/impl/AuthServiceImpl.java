package com.ryanmiao.airflow.security.service.impl;

import com.ryanmiao.airflow.security.config.UserClient;
import com.ryanmiao.airflow.security.service.IAuthService;
import org.springframework.stereotype.Service;

/**
 * @author Ryan Miao
 * @date 2019/5/30 20:07
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private final UserClient userClient;

    public AuthServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public void logout(String token) {
        userClient.logOut(token);
    }
}
