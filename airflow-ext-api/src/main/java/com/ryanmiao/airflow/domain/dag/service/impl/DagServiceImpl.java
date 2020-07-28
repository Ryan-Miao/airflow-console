package com.ryanmiao.airflow.domain.dag.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryanmiao.airflow.domain.common.vo.PageResult;
import com.ryanmiao.airflow.domain.dag.connector.AirflowClient;
import com.ryanmiao.airflow.domain.dag.connector.contract.AirflowRestApiRs;
import com.ryanmiao.airflow.domain.dag.connector.contract.DeleteDagRs;
import com.ryanmiao.airflow.domain.dag.entity.Dag;
import com.ryanmiao.airflow.domain.dag.mapper.DagMapper;
import com.ryanmiao.airflow.domain.dag.service.IDagService;
import com.ryanmiao.airflow.domain.dag.vo.DagListRq;
import com.ryanmiao.airflow.domain.ext.service.IPyGenerator;
import com.ryanmiao.airflow.exception.BizException;
import com.ryanmiao.airflow.log.SystemEvent;
import com.ryanmiao.airflow.utils.PageUtils;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Map;

/**
 * @author Ryan Miao
 * @date 2019/7/1 9:57
 */
@Service
public class DagServiceImpl implements IDagService {

    private final DagMapper dagMapper;
    private final IPyGenerator pyGenerator;
    private final AirflowClient airflowClient;

    @Autowired
    public DagServiceImpl(DagMapper dagMapper, IPyGenerator pyGenerator,
                          AirflowClient airflowClient) {
        this.dagMapper = dagMapper;
        this.pyGenerator = pyGenerator;
        this.airflowClient = airflowClient;
    }

    @Override
    public PageResult<Dag> list(DagListRq dag) {

        Example example = new Example(Dag.class);
        Criteria criteria = example.createCriteria();
        if (dag != null) {
            String dagId = dag.getDagId();
            String owners = dag.getOwners();
            if (StringUtils.isNotBlank(dagId)) {
                criteria.andLike("dagId", "%" + dagId + "%");
            }

            if (StringUtils.isNotBlank(owners)) {
                criteria.andLike("owners", "%" + owners + "%");
            }
        }

        assert dag != null;
        Page<Dag> objects = PageHelper
            .startPage(dag.getPageRq().getPageNum(), dag.getPageRq().getPageSize())
            .doSelectPage(() -> dagMapper.selectByExample(example));
        return PageUtils.page(objects);

    }

    @Override
    public void deleteGit(String id) {
        Dag dag = dagMapper.selectByPrimaryKey(id);
        String fileloc = dag.getFileloc();
        String prefix = "/usr/local/airflow/dags/";
        if (fileloc.startsWith(prefix)) {
            String fileName = fileloc.substring(prefix.length());
            pyGenerator.delete(fileName);
        }
    }

    @Override
    public void deleteRecord(String id) {
        DeleteDagRs deleteDagRs = airflowClient.deleteDagRecord(id);
        if (deleteDagRs.getError() != null) {
            throw new BizException(deleteDagRs.getError(),
                SystemEvent.DELETE_DAG_RECORDER_FROM_AIRFLOW_FAILED.getCode());
        }

    }

    @Override
    public AirflowRestApiRs restApi(String api, Map<String, Object> params) {
        return airflowClient.restApi(api, params);
    }
}
