package com.ryanmiao.airflow.domain.ext.entity;

import java.time.LocalDateTime;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ryan
 * @date 19-9-11 上午11:37
 */
@Data
@Accessors(chain = true)
@Table(name = "ext_dag_conf")
public class ExtDag {
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer categoryId;
    private String cron;
    private String owners;
    private Boolean dependsOnPast;
    /**
     * yyyy-MM-dd
     */
    private String startDate;
    /**
     * dingding conn
     */
    private String alertDing;
    /**
     * 统一账号,逗号分割. 比如ryanmiao,aaronlai
     */
    private String alertMailReceivers;

    /**
     * dingding, email
     * @see AlertType
     */
    private String successAlert;

    /**
     * dingding, email
     * @see AlertType
     */
    private String failureAlert;

    /**
     * t1 >> t2; [t3, t4] >> t5
     */
    private String taskDependency;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
