package com.ryanmiao.airflow.exception;

/**
 * 登录失败，返回401.
 * @author miaoruifeng
 */
public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
