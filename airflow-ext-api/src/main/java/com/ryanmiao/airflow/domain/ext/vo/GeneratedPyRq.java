package com.ryanmiao.airflow.domain.ext.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ryan
 * @date 19-9-19 下午4:19
 */
@Data
@Accessors(chain = true)
public class GeneratedPyRq {

    @NotNull
    @NotEmpty
    private String categoryName;
    /**
     * python filename, only filename
     */
    @NotNull
    @NotEmpty
    private String dagFileName;
    /**
     * python content
     */
    @NotNull
    @NotEmpty
    private String dagPyBody;

}
