package com.ryanmiao.airflow.domain.common.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页数据结果集
 *
 * @param <T> 结果集项的类型
 * @author Ryan Miao
 */
@Data
public class PageResult<T> {
    private static final long serialVersionUID = 9056411043515781783L;

    /**
     * 页码，0表示第一页
     */
    private int pageNum;
    /**
     * 每页结果数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总数
     */
    private long total;

    /**
     * 数据
     */
    private List<T> list;

}

