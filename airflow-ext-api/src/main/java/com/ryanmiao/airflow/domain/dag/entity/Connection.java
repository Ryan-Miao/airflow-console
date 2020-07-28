package com.ryanmiao.airflow.domain.dag.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * airflow 连接池属性, 存储数据库账号密码等
 * @author ryan
 * @date 19-9-12 下午2:10
 */
@Data
@Accessors(chain = true)
public class Connection {

    private Integer id;
    private String connId;
    private String connType;
    private String host;
    private String schema;
    private String login;
    private String password;
    private Integer port;
    private String extra;
    private Boolean isEncrypted;
    private Boolean isExtraEncrypted;

}
