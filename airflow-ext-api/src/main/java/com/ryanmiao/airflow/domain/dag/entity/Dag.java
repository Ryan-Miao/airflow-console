package com.ryanmiao.airflow.domain.dag.entity;

import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.Data;

/**
 * @author Ryan Miao
 * @date 2019/7/1 9:53
 */
@Data
public class Dag {

    @Id
    private String dagId;
    private String owners;
    private String description;
    private String scheduleInterval;
    private String fileloc;
    private Boolean isPaused;
    private Boolean isSubdag;
    private Boolean isActive;
    private LocalDateTime lastSchedulerRun;

}
