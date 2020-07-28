package com.ryanmiao.airflow.log;

import java.util.Arrays;

/**
 * @author Ryan Miao
 */
@SuppressWarnings("PMD")
public enum SystemEvent {
    UN_KNOWN(100, "未知错误"),
    INTERNAL_SERVER_ERROR(500, "系统内部错误"),
    UN_AUTHORIZED_ERROR(401, "认证失败"),
    PERMISSION_NOT_ALLOWD_ERROR(403, "权限不足，不允许访问"),
    PARAM_INVALID_ERROR(400, "参数不符合条件"),

    DAG_ALREADY_EXISTS_ERROR(10000, "DAG id已经存在"),
    DAG_TASK_ALREADY_EXISTS_ERROR(10001, "DAG中task id已经存在"),
    CATEGORY_ALREADY_EXISTS_ERROR(10002, "category已经存在"),
    PUSH_FILE_REMOTE_GIT_FAILED(10003, "推送dag.py到远程git仓库失败"),
    DELETE_FILE_REMOTE_GIT_FAILED(10004, "删除文件到远程git仓库失败"),
    DELETE_DAG_RECORDER_FROM_AIRFLOW_FAILED(10005, "从airflow删除dag记录失败"),

    ;
    private Integer code;
    private String msg;

    SystemEvent(Integer code, String description) {
        this.code = code;
        this.msg = description;
    }

    public static SystemEvent findEvent(Integer code) {
        return Arrays.stream(SystemEvent.values()).filter(e -> e.getCode().equals(code)).findFirst()
            .orElse(UN_KNOWN);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name();
    }

    public String getMsg() {
        return this.msg;
    }
}
