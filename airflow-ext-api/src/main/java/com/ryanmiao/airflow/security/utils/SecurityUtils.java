package com.ryanmiao.airflow.security.utils;

import com.ryanmiao.airflow.security.vo.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ryan
 * @date 19-9-17 上午10:50
 */
public class SecurityUtils {

    public static UserInfo currentUser() {
        return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    public static String getCurrentUserName() {
        return currentUser().getUsername();
    }

}
