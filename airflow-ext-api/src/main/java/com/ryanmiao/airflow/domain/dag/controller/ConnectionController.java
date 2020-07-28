package com.ryanmiao.airflow.domain.dag.controller;

import com.ryanmiao.airflow.domain.common.vo.BaseResponse;
import com.ryanmiao.airflow.domain.dag.entity.Connection;
import com.ryanmiao.airflow.domain.dag.service.IConnectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ryan
 * @date 19-9-12 下午2:08
 */
@RequestMapping("/v1/conns")
@RestController
public class ConnectionController {

    private final IConnectionService connectionService;

    public ConnectionController(
        IConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping()
    public BaseResponse<List<Connection>> list(@RequestParam(required = false) String type,
                                               @RequestParam(required = false) String connId) {

        List<Connection> list = connectionService.list(type, connId);
        return new BaseResponse<>(list);
    }

}
