package com.ryanmiao.airflow.domain.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Ryan Miao
 * @date 2020/7/28 14:08
 */
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CODE_SUCCESS = 0;
    private int code;
    private String msg;
    private T data;

    public BaseResponse(T data) {
        this.data = data;
        this.code = CODE_SUCCESS;
        this.msg = "OK";
    }

    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(data);
    }

    public static <T> BaseResponse<T> fail(int code, String msg) {
        return new BaseResponse<>(code, msg);
    }

}
