package com.ryanmiao.airflow.utils;

import com.github.pagehelper.Page;
import com.ryanmiao.airflow.domain.common.vo.PageResult;

/**
 * @author ryan
 * @date 19-9-11 下午5:42
 */
public class PageUtils {

    public static <T> PageResult<T> page(Page<T> page) {
        PageResult<T> p = new PageResult<>();
        p.setPageNum(page.getPageNum());
        p.setPageSize(page.getPageSize());
        p.setTotal(page.getTotal());
        p.setTotalPage(page.getPages());
        p.setList(page.getResult());
        return p;
    }

}
