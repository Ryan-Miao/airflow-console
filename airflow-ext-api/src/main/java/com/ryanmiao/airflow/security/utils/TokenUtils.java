package com.ryanmiao.airflow.security.utils;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Token管理工具类.
 *
 * @author Ryan Miao at 2018-06-27 10:04
 **/
public class TokenUtils {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_ = "Bearer ";
    public static final String TOKEN = "token";

    /**
     * 从reques中读取token. 优先读取header[Authorization]，读取后截取Bearer 之后的内容为token. 其次，读取参数[token],
     * 再次，读取cookie[token].
     */
    public static String readTokenFromRequest(HttpServletRequest request) {

        final String authorization = request.getHeader(AUTHORIZATION);
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith(BEARER_)) {
            return authorization.substring(7);
        }

        final String token = request.getParameter(TOKEN);
        if (StringUtils.isNotBlank(token)) {
            return token;
        }

        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                .filter(cookie -> TOKEN.equalsIgnoreCase(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst().orElse("");
        }

        return "";
    }

    /**
     * 将token写入cookie.
     */
    public static void writeTokenToCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(TokenUtils.TOKEN, token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
    }

    /**
     * 清空cookie.
     */
    public static void cleanTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(TokenUtils.TOKEN, null);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
    }

}
