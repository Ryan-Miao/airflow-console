package com.ryanmiao.airflow.security.controller;

import com.ryanmiao.airflow.domain.common.vo.BaseResponse;
import com.ryanmiao.airflow.security.service.IAuthService;
import com.ryanmiao.airflow.security.utils.TokenUtils;
import com.ryanmiao.airflow.security.vo.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ryan Miao
 * @date 2019/6/5 16:38
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final IAuthService authService;

    @Value("${page.index}")
    private String indexPage;

    public UserController(
        IAuthService authService) {
        this.authService = authService;
    }


    @ApiOperation(value = "当前登录用户信息")
    @GetMapping("/current")
    public BaseResponse<UserInfo> currentUser(
        @ApiParam(hidden = true) Authentication authentication) {
        UserInfo details = (UserInfo) authentication.getDetails();
        return new BaseResponse<>(details);

    }

    @ApiOperation(value = "退出登陆")
    @GetMapping("/logout")
    public BaseResponse<Boolean> logout(@ApiParam(hidden = true) Authentication authentication) {
        UserInfo details = (UserInfo) authentication.getDetails();
        String token = details.getToken();
        authService.logout(token);
        return new BaseResponse<>(true);

    }

    @ApiOperation(value = "登陆成功后跳转到首页")
    @GetMapping("index")
    public void index(@RequestParam String token, HttpServletResponse response) throws IOException {
        TokenUtils.writeTokenToCookie(response, token);
        response.sendRedirect(indexPage);
    }


}
