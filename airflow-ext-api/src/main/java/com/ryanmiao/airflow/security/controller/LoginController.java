package com.ryanmiao.airflow.security.controller;

import com.ryanmiao.airflow.domain.common.vo.BaseResponse;
import com.ryanmiao.airflow.exception.UnAuthorizedException;
import com.ryanmiao.airflow.security.service.IAuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan Miao
 * @date 2019/5/29 22:44
 */
@RestController
@RequestMapping("/v1/auth")
public class LoginController {

    private final IAuthService authService;

    public LoginController(
        IAuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("error")
    public BaseResponse<String> error() {
        throw new UnAuthorizedException("登录失效");
    }


}
