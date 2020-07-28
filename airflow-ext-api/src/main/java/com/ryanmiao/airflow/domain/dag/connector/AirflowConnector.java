package com.ryanmiao.airflow.domain.dag.connector;

import com.ryanmiao.airflow.domain.dag.connector.contract.AirflowRestApiRs;
import com.ryanmiao.airflow.domain.dag.connector.contract.DeleteDagRs;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import java.util.Map;

/**
 * @author ryan
 * @date 19-9-21 上午11:52
 */
public interface AirflowConnector {

    /**
     * 从airflow删除dag记录.
     *
     * @param id dag id
     * @return result
     */
    @RequestLine("DELETE /api/experimental/dags/{id}")
    DeleteDagRs deleteRecord(@Param("id") String id);

    /**
     * airflow rest api
     * @param api api
     * @param queryParam param
     * @return rs
     */
    @RequestLine("GET /admin/rest_api/api?api={api}")
    AirflowRestApiRs restApi(@Param("api") String api, @QueryMap Map<String, Object> queryParam);

}
