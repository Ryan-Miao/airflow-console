package com.ryanmiao.airflow.config;

import lombok.Data;

/**
 * @author Ryan Miao
 * @date 2019/6/4 14:58
 */
@Data
public class AuthProperties {

    private String url;
    private String app;
    private String forgetPassUrl;
    private String loginUrl;

}
