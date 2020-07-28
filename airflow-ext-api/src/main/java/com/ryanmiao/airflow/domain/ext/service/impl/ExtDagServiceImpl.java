package com.ryanmiao.airflow.domain.ext.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ryanmiao.airflow.domain.common.vo.PageResult;
import com.ryanmiao.airflow.domain.ext.entity.ExtDag;
import com.ryanmiao.airflow.domain.ext.entity.ExtDagTask;
import com.ryanmiao.airflow.domain.ext.mapper.ExtDagMapper;
import com.ryanmiao.airflow.domain.ext.mapper.ExtDagTaskMapper;
import com.ryanmiao.airflow.domain.ext.service.IExtDagService;
import com.ryanmiao.airflow.domain.ext.vo.ExtDagListRq;
import com.ryanmiao.airflow.log.SystemEvent;
import com.ryanmiao.airflow.security.utils.SecurityUtils;
import com.ryanmiao.airflow.utils.ExceptionUtils;
import com.ryanmiao.airflow.utils.PageUtils;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ryan
 * @date 19-9-11 下午2:36
 */
@Service
public class ExtDagServiceImpl implements IExtDagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtDagServiceImpl.class);

    private final ExtDagMapper mapper;
    private final ExtDagTaskMapper taskMapper;

    public ExtDagServiceImpl(ExtDagMapper mapper,
                             ExtDagTaskMapper taskMapper) {
        this.mapper = mapper;
        this.taskMapper = taskMapper;
    }

    @Override
    public PageResult<ExtDag> list(ExtDagListRq rq) {
        Example example = new Example(ExtDag.class);
        Criteria criteria = example.createCriteria();
        if (rq != null) {
            Long dagId = rq.getDagId();
            String owners = rq.getOwners();
            String dagName = rq.getDagName();
            if (dagId != null) {
                criteria.andEqualTo("id", dagId);
            }
            if (StringUtils.isNotBlank(dagName)) {
                criteria.andLike("name", "%" + dagName + "%");
            }

            if (StringUtils.isNotBlank(owners)) {
                criteria.andLike("owners", "%" + owners + "%");
            }
        }

        assert rq != null;
        Page<ExtDag> objects = PageHelper
            .startPage(rq.getPageRq().getPageNum(), rq.getPageRq().getPageSize())
            .doSelectPage(() -> mapper.selectByExample(example));
        return PageUtils.page(objects);
    }

    @Override
    public void add(ExtDag extDag) {
        LocalDateTime now = LocalDateTime.now();
        extDag.setCreateTime(now);
        extDag.setUpdateTime(now);
        try {
            mapper.insertSelective(extDag);
        } catch (DuplicateKeyException ignore) {
            ExceptionUtils.exception(SystemEvent.DAG_ALREADY_EXISTS_ERROR);
        }
    }

    @Override
    public void update(ExtDag extDag) {
        extDag.setUpdateTime(LocalDateTime.now());
        extDag.setCreateTime(null);

        try {
            mapper.updateByPrimaryKeySelective(extDag);
        } catch (DuplicateKeyException ignore) {
            ExceptionUtils.exception(SystemEvent.DAG_ALREADY_EXISTS_ERROR);
        }
    }

    @Override
    public ExtDag getById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ExtDagTask> taskList(ExtDagTask extDagTask) {
        Example example = new Example(ExtDagTask.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dagId", extDagTask.getDagId());
        if (extDagTask.getTaskType() != null) {
            criteria.andEqualTo("taskType", extDagTask.getTaskType());
        }
        if (StringUtils.isNotBlank(extDagTask.getTaskId())) {
            criteria.andLike("taskId", extDagTask.getTaskId());
        }

        return taskMapper.selectByExample(example);
    }

    @Override
    public void addTask(ExtDagTask extDagTask) {
        LocalDateTime now = LocalDateTime.now();
        extDagTask.setCreateTime(now);
        extDagTask.setUpdateTime(now);

        LOGGER.info("user {} add task {}", SecurityUtils.getCurrentUserName(), extDagTask);

        try {
            taskMapper.insertSelective(extDagTask);
        } catch (DuplicateKeyException ignore) {
            ExceptionUtils.exception(SystemEvent.DAG_TASK_ALREADY_EXISTS_ERROR);
        }
    }

    @Override
    public void updateTask(ExtDagTask extDagTask) {
        extDagTask.setUpdateTime(LocalDateTime.now());
        extDagTask.setCreateTime(null);
        LOGGER.info("user {} update task {}", SecurityUtils.getCurrentUserName(), extDagTask);
        taskMapper.updateByPrimaryKeySelective(extDagTask);
    }

    @Override
    public void deleteTask(Long dagId, String taskId) {
        taskMapper.deleteByPrimaryKey(new ExtDagTask().setDagId(dagId).setTaskId(taskId));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteDag(Long id) {
        taskMapper.delete(new ExtDagTask().setDagId(id));
        mapper.delete(new ExtDag().setId(id));

    }
}
