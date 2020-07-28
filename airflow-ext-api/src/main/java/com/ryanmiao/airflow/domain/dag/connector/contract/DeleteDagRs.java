package com.ryanmiao.airflow.domain.dag.connector.contract;

import lombok.Data;

/**
 * @author ryan
 * @date 19-9-21 上午11:56
 */
@Data
public class DeleteDagRs {

    private Integer count;
    private String message;
    private String error;

}
