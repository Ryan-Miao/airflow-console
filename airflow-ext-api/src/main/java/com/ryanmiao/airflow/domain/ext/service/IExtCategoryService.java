package com.ryanmiao.airflow.domain.ext.service;

import com.ryanmiao.airflow.domain.ext.constant.CategoryStatusEnum;
import com.ryanmiao.airflow.domain.ext.entity.ExtDagCategory;
import java.util.List;

/**
 * Dag分类
 *
 * @author ryan
 * @date 19-9-18 下午3:54
 */
public interface IExtCategoryService {

    /**
     * list category
     *
     * @param status status
     * @param name name
     * @return list
     */
    List<ExtDagCategory> list(CategoryStatusEnum status, String name);

    /**
     * add category
     *
     * @param category category
     */
    void add(ExtDagCategory category);

    /**
     * update category
     *
     * @param category category
     */
    void update(ExtDagCategory category);

    /**
     * deleteGit category
     *
     * @param id id
     */
    void delete(Integer id);
}
