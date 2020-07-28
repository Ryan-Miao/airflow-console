package com.ryanmiao.airflow.domain.ext.entity;

import com.ryanmiao.airflow.domain.ext.constant.CategoryStatusEnum;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * @author ryan
 * @date 19-9-18 下午3:53
 */
@Data
@Accessors(chain = true)
public class ExtDagCategory {

    @Id
    private Integer id;
    private String name;
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private CategoryStatusEnum status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
