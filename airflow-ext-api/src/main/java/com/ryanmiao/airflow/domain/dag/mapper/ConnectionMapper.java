package com.ryanmiao.airflow.domain.dag.mapper;

import com.ryanmiao.airflow.domain.dag.entity.Connection;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author ryan
 * @date 19-9-12 下午2:16
 */
@Component
public interface ConnectionMapper extends Mapper<Connection> {

}
