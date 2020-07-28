package com.ryanmiao.airflow.exception;

/**
 * 授权失败，forbidden, 权限不足，比如app不属于该用户. 返回403
 * @author miaoruifeng
 */
public class PermissionNotAllowedException extends RuntimeException {
    public PermissionNotAllowedException(String message) {
        super(message);
    }
}