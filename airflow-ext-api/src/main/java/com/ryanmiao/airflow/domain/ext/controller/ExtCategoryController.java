package com.ryanmiao.airflow.domain.ext.controller;

import com.ryanmiao.airflow.domain.common.vo.BaseResponse;
import com.ryanmiao.airflow.domain.ext.constant.CategoryStatusEnum;
import com.ryanmiao.airflow.domain.ext.entity.ExtDagCategory;
import com.ryanmiao.airflow.domain.ext.service.IExtCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ryan
 * @date 19-9-18 下午3:52
 */
@RestController
@RequestMapping("/v1/ext/categories")
public class ExtCategoryController {

    private final IExtCategoryService categoryService;

    public ExtCategoryController(
        IExtCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public BaseResponse<List<ExtDagCategory>> list(CategoryStatusEnum status, String name) {
        List<ExtDagCategory> list = categoryService.list(status, name);
        return new BaseResponse<>(list);
    }


    @PostMapping("")
    public BaseResponse<Boolean> add(@Valid @RequestBody ExtDagCategory category) {
        categoryService.add(category);
        return new BaseResponse<>(true);
    }

    @PutMapping("")
    public BaseResponse<Boolean> update(@Valid @RequestBody ExtDagCategory category) {
        categoryService.update(category);
        return new BaseResponse<>(true);
    }

    @DeleteMapping("{id}")
    public BaseResponse<Boolean> update(@PathVariable Integer id) {
        categoryService.delete(id);
        return new BaseResponse<>(true);
    }

}
