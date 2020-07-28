package com.ryanmiao.airflow.domain.dag.service.impl;

import com.ryanmiao.airflow.domain.dag.entity.Connection;
import com.ryanmiao.airflow.domain.dag.mapper.ConnectionMapper;
import com.ryanmiao.airflow.domain.dag.service.IConnectionService;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

/**
 * @author ryan
 * @date 19-9-12 下午2:16
 */
@Service
public class ConnectionServiceImpl implements IConnectionService {

    private final ConnectionMapper mapper;

    public ConnectionServiceImpl(ConnectionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Connection> list(String type, String connId) {

        Example example = new Example(Connection.class);
        Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(type)) {
            criteria.andLike("connType", "%" + type + "%");
        }
        if (StringUtils.isNotBlank(connId)) {
            criteria.andLike("connId", "%" + connId + "%");
        }

        return mapper.selectByExample(example);
    }
}
