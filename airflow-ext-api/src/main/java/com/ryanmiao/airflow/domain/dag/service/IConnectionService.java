package com.ryanmiao.airflow.domain.dag.service;

import com.ryanmiao.airflow.domain.dag.entity.Connection;
import java.util.List;

/**
 * @author ryan
 * @date 19-9-12 下午2:14
 */
public interface IConnectionService {

    /**
     * list conn.
     * @param type conn type,  mysql,postgres, http, etc
     * @param connId conn_id
     * @return list
     */
    List<Connection> list(String type, String connId);
}
