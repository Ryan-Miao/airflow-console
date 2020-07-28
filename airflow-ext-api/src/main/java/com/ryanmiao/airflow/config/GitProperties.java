package com.ryanmiao.airflow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ryan
 * @date 19-9-19 下午4:38
 */
@Data
@Component
@ConfigurationProperties(prefix = "git")
public class GitProperties {

    private String remote;
    private String username;
    private String password;

}
