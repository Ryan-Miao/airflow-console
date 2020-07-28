package com.ryanmiao.airflow.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 跨域配置.
 *
 * @author miaoruifeng
 */
@Component
@Order(SecurityProperties.DEFAULT_FILTER_ORDER - 10)
@WebFilter(urlPatterns = "/**")
public class CorsConfigFilter extends OncePerRequestFilter {

    public static final String METHOD_OPTIONS = "OPTIONS";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String origin = request.getHeader("origin");

        if (StringUtils.isBlank(origin)) {
            filterChain.doFilter(request, response);
            return;
        }

        String newOrigin = getOrigin(origin);

        response.setHeader("Access-Control-Allow-Origin", newOrigin);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response
            .setHeader("Access-Control-Allow-Headers",
                "authorization, content-type, xsrf-token");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        if (METHOD_OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private String getOrigin(String origin) {
        return origin;
    }
}
