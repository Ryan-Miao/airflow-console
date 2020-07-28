package com.ryanmiao.airflow.domain.ext.mapper;

import com.ryanmiao.airflow.domain.ext.entity.ExtDagTask;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author ryan
 * @date 19-9-11 下午3:18
 */
@Component
public interface ExtDagTaskMapper extends Mapper<ExtDagTask> {

}
