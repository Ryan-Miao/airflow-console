package com.ryanmiao.airflow.domain.dag.service;

import com.ryanmiao.airflow.domain.common.vo.PageResult;
import com.ryanmiao.airflow.domain.dag.connector.contract.AirflowRestApiRs;
import com.ryanmiao.airflow.domain.dag.entity.Dag;
import com.ryanmiao.airflow.domain.dag.vo.DagListRq;

import java.util.Map;

/**
 * @author Ryan Miao
 * @date 2019/7/1 9:55
 */
public interface IDagService {


    /**
     * list dag
     *
     * @param dag 过滤参数，id, owner
     * @return list
     */
    PageResult<Dag> list(DagListRq dag);

    /**
     * deleteGit dag file
     *
     * @param id dag id
     */
    void deleteGit(String id);

    /**
     * airflow delete dag:
     * 1. delete file from git,
     *
     * @param id dag id
     * @see IDagService#deleteGit(java.lang.String)
     * 2. delete record from airflow
     */
    void deleteRecord(String id);

    /**
     * Airflow rest api透传
     *
     * @param api    api
     * @param params 参数
     * @return rs
     */
    AirflowRestApiRs restApi(String api, Map<String, Object> params);
}
