package com.ryanmiao.airflow.domain.ext.vo;

import com.ryanmiao.airflow.domain.common.vo.PageRq;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author ryan
 * @date 19-9-10 下午4:57
 */
@Data
public class ExtDagListRq {

    private Long dagId;
    private String dagName;
    private String owners;

    @NotNull
    private PageRq pageRq;

}
