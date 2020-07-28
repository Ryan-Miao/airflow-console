package com.ryanmiao.airflow.domain.ext.entity;

/**
 * @author ryan
 * @date 19-9-11 下午2:04
 */
public enum ExtDagTaskType {
    /**
     * bash
     */
    BashOperator,
    /**
     * hive
     */
    HiveOperator,
    /**
     * 入库hive
     */
    RDBMS2HiveOperator,
    /**
     * hive出库pg
     */
    Hive2RDBMSOperator,
    /**
     * http任务
     */
    SimpleHttpOperator,
    /**
     * dag依赖
     */
    ExternalTaskSensor,
    /**
     * http依赖
     */
    HttpSensor,
    /**
     * 罗盘调度系统依赖
     */
    CompassTaskSensor

}
