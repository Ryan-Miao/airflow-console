package com.ryanmiao.airflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 自动生成API文档配置.
 *
 * @author miaoruifeng
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Value("${swagger.enable}")
    private boolean enableSwagger;


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Demo API")
            .description("airflow-ext-api API")
            .termsOfServiceUrl("")
            .version("1.0.0")
            .build();
    }

    /**
     * doc.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .enable(enableSwagger)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .build()
            .apiInfo(apiInfo());
    }

}
