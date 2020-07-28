package com.ryanmiao.airflow.domain.ext.service;

import com.ryanmiao.airflow.domain.common.vo.PageResult;
import com.ryanmiao.airflow.domain.ext.entity.ExtDag;
import com.ryanmiao.airflow.domain.ext.entity.ExtDagTask;
import com.ryanmiao.airflow.domain.ext.vo.ExtDagListRq;

import java.util.List;

/**
 * ext dag相关操作
 *
 * @author ryan
 * @date 19-9-11 下午2:35
 */
public interface IExtDagService {

    /**
     * list
     *
     * @param rq rq
     * @return page
     */
    PageResult<ExtDag> list(ExtDagListRq rq);

    /**
     * add
     *
     * @param extDag dag
     */
    void add(ExtDag extDag);

    /**
     * update
     *
     * @param extDag dag
     */
    void update(ExtDag extDag);

    /**
     * get ext dag by id
     *
     * @param id dag id
     * @return dag
     */
    ExtDag getById(Long id);

    /**
     * get ext dat task list
     *
     * @param extDagTask query
     * @return list
     */
    List<ExtDagTask> taskList(ExtDagTask extDagTask);

    /**
     * add task
     *
     * @param extDagTask task
     */
    void addTask(ExtDagTask extDagTask);

    /**
     * update task
     *
     * @param extDagTask task
     */
    void updateTask(ExtDagTask extDagTask);

    /**
     * deleteGit task
     *
     * @param dagId  dag id
     * @param taskId task id
     */
    void deleteTask(Long dagId, String taskId);

    /**
     * deleteGit dag by id
     *
     * @param id dag id
     */
    void deleteDag(Long id);
}
