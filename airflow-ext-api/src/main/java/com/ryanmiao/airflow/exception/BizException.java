package com.ryanmiao.airflow.exception;

/**
 * @author miaoruifeng
 */
public class BizException extends RuntimeException {

    private Integer code;

    public BizException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
