package com.ryanmiao.airflow.domain.dag.controller;

import com.ryanmiao.airflow.domain.common.vo.BaseResponse;
import com.ryanmiao.airflow.domain.common.vo.PageResult;
import com.ryanmiao.airflow.domain.dag.connector.contract.AirflowRestApiRs;
import com.ryanmiao.airflow.domain.dag.entity.Dag;
import com.ryanmiao.airflow.domain.dag.service.IDagService;
import com.ryanmiao.airflow.domain.dag.vo.DagListRq;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Ryan Miao
 * @date 2019/7/1 9:51
 */
@RestController
@RequestMapping("/v1/dag")
public class DagController {

    private final IDagService dagService;

    public DagController(IDagService dagService) {
        this.dagService = dagService;
    }

    @PostMapping("list")
    public BaseResponse<PageResult<Dag>> list(@Valid @RequestBody DagListRq rq) {
        PageResult<Dag> list = dagService.list(rq);
        return new BaseResponse<>(list);
    }

    @DeleteMapping("delete-git")
    public BaseResponse<Boolean> deleteGit(@RequestParam String id) {
        dagService.deleteGit(id);
        return new BaseResponse<>(true);
    }

    @DeleteMapping("delete-record")
    public BaseResponse<Boolean> deleteRecord(@RequestParam String id) {
        dagService.deleteRecord(id);

        return new BaseResponse<>(true);
    }

    @PostMapping("rest-api")
    @ApiOperation("airflow restful api")
    public BaseResponse<AirflowRestApiRs> restApi(@RequestParam String api,
                                                  @RequestBody Map<String, Object> params) {
        AirflowRestApiRs rs = dagService.restApi(api, params);

        return new BaseResponse<>(rs);
    }

}
