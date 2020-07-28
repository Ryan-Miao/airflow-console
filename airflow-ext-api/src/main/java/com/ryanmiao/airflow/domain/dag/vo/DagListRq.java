package com.ryanmiao.airflow.domain.dag.vo;

import com.ryanmiao.airflow.domain.common.vo.PageRq;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author ryan
 * @date 19-9-10 下午4:57
 */
@Data
public class DagListRq {

    private String dagId;
    private String owners;

    @NotNull
    private PageRq pageRq;

}
