package com.ryanmiao.airflow.domain.ext.mapper;

import com.ryanmiao.airflow.domain.ext.entity.ExtDagCategory;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author ryan
 * @date 19-9-18 下午3:56
 */
@Component
public interface ExtDagCategoryMapper extends Mapper<ExtDagCategory> {

}
