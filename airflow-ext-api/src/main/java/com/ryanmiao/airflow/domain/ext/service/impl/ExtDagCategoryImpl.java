package com.ryanmiao.airflow.domain.ext.service.impl;

import com.ryanmiao.airflow.domain.ext.constant.CategoryStatusEnum;
import com.ryanmiao.airflow.domain.ext.entity.ExtDagCategory;
import com.ryanmiao.airflow.domain.ext.mapper.ExtDagCategoryMapper;
import com.ryanmiao.airflow.domain.ext.service.IExtCategoryService;
import com.ryanmiao.airflow.log.SystemEvent;
import com.ryanmiao.airflow.utils.ExceptionUtils;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ryan
 * @date 19-9-18 下午3:56
 */
@Service
public class ExtDagCategoryImpl implements IExtCategoryService {

    private final ExtDagCategoryMapper mapper;

    public ExtDagCategoryImpl(ExtDagCategoryMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<ExtDagCategory> list(CategoryStatusEnum status, String name) {

        Example example = new Example(ExtDagCategory.class);
        Criteria criteria = example.createCriteria();
        if (status != null) {
            criteria.andEqualTo("status", status.name());
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }

        return mapper.selectByExample(example);
    }

    @Override
    public void add(ExtDagCategory category) {
        LocalDateTime now = LocalDateTime.now();
        category.setCreateTime(now).setUpdateTime(now).setId(null);
        try {
            mapper.insertSelective(category);
        } catch (DuplicateKeyException ignore) {
            ExceptionUtils.exception(SystemEvent.CATEGORY_ALREADY_EXISTS_ERROR);
        }
    }

    @Override
    public void update(ExtDagCategory category) {
        category.setCreateTime(null)
            .setUpdateTime(LocalDateTime.now());

        mapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void delete(Integer id) {
        ExtDagCategory category = new ExtDagCategory().setId(id)
            .setUpdateTime(LocalDateTime.now())
            .setStatus(CategoryStatusEnum.deleted);
        mapper.updateByPrimaryKeySelective(category);

    }
}
