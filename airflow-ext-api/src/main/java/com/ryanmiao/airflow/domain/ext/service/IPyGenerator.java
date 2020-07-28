package com.ryanmiao.airflow.domain.ext.service;

import com.ryanmiao.airflow.domain.ext.vo.GeneratedPyRq;

/**
 * @author ryan
 * @date 19-9-19 下午4:24
 */
public interface IPyGenerator {

    /**
     * push to gitlab
     *
     * @param rq rq
     * @return filename
     */
    String push(GeneratedPyRq rq);

    /**
     * deleteGit dag from gitlab
     *
     * @param fileName to deleteGit
     */
    void delete(String fileName);
}
