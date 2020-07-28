package com.ryanmiao.airflow.domain.ext.entity;

import java.time.LocalDateTime;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * @author ryan
 * @date 19-9-11 下午2:03
 */
@Data
@Accessors(chain = true)
public class ExtDagTask {

    @Id
    @NotNull
    private Long dagId;
    @NotNull
    @NotEmpty
    @Id
    private String taskId;
    private String description;
    /**
     * 插件类型
     *
     * @see ExtDagTaskType
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private ExtDagTaskType taskType;

    /**
     * plugin参数, json格式
     */
    private String param;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
