package com.ryanmiao.airflow.utils;

import com.ryanmiao.airflow.exception.BizException;
import com.ryanmiao.airflow.log.SystemEvent;

/**
 * @author ryan
 * @date 19-9-12 下午5:15
 */
public class ExceptionUtils {

    public static void exception(SystemEvent event) {
        String msg = "Error Code: " + event.getCode() + "; details: " + event.getMsg();
        throw new BizException(msg, event.getCode());
    }

}
