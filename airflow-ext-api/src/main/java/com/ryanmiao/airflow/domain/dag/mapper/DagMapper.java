package com.ryanmiao.airflow.domain.dag.mapper;

import com.ryanmiao.airflow.domain.dag.entity.Dag;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Ryan Miao
 * @date 2019/7/1 9:57
 */
@Component
public interface DagMapper extends Mapper<Dag> {

}
