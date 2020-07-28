package com.ryanmiao.airflow.security.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ryan Miao
 * @date 2019/5/30 20:06
 */
@Data
@Accessors(chain = true)
public class UserToken {

    private String token;

}
