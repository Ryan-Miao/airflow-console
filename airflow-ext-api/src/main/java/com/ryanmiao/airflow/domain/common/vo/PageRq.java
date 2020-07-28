package com.ryanmiao.airflow.domain.common.vo;

import lombok.Data;

/**
 * 通用page 参数
 * @author ryan
 * @date 19-9-11 下午5:45
 */
@Data
public class PageRq {

    private Integer pageNum;
    private Integer pageSize;

}
