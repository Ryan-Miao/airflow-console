package com.ryanmiao.airflow;

import java.io.IOException;
import java.util.TimeZone;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Ryan Miao
 * @date 2019/7/1 9:26
 */
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.ryanmiao.airflow.domain.dag.mapper")
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class Application {

    public static ApplicationContext context;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        context = SpringApplication.run(Application.class, args);
    }

    @Controller
    public static class HomeController {

        @GetMapping({"/"})
        public void api(HttpServletResponse response) throws IOException {
            response.sendRedirect("/api/swagger-ui.html");
        }
    }

}
