package com.ryanmiao.airflow.domain.ext.controller;

import com.ryanmiao.airflow.domain.common.vo.BaseResponse;
import com.ryanmiao.airflow.domain.common.vo.PageResult;
import com.ryanmiao.airflow.domain.ext.entity.ExtDag;
import com.ryanmiao.airflow.domain.ext.entity.ExtDagTask;
import com.ryanmiao.airflow.domain.ext.service.IExtDagService;
import com.ryanmiao.airflow.domain.ext.service.IPyGenerator;
import com.ryanmiao.airflow.domain.ext.vo.ExtDagListRq;
import com.ryanmiao.airflow.domain.ext.vo.GeneratedPyRq;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Ext dag list/view/
 *
 * @author ryan
 * @date 19-9-11 上午11:37
 */
@RestController
@RequestMapping("/v1/ext/dags")
public class ExtDagController {

    private final IExtDagService extDagService;
    private final IPyGenerator pyGenerator;

    public ExtDagController(IExtDagService extDagService,
                            IPyGenerator pyGenerator) {
        this.extDagService = extDagService;
        this.pyGenerator = pyGenerator;
    }

    @PostMapping("list")
    public BaseResponse<PageResult<ExtDag>> list(@Valid @RequestBody ExtDagListRq rq) {

        PageResult<ExtDag> list = extDagService.list(rq);

        return new BaseResponse<>(list);

    }

    @GetMapping("get/{id}")
    public BaseResponse<ExtDag> getById(@PathVariable Long id) {

        ExtDag list = extDagService.getById(id);

        return new BaseResponse<>(list);

    }

    @PostMapping("add")
    public BaseResponse<Boolean> add(@RequestBody ExtDag extDag) {

        extDagService.add(extDag);

        return new BaseResponse<>(true);

    }

    @PutMapping("update")
    public BaseResponse<Boolean> update(@RequestBody ExtDag extDag) {

        extDagService.update(extDag);

        return new BaseResponse<>(true);
    }

    @DeleteMapping("delete/{id}")
    public BaseResponse<Boolean> delete(@PathVariable Long id) {
        extDagService.deleteDag(id);
        return new BaseResponse<>(true);
    }


    @PostMapping("tasks")
    public BaseResponse<List<ExtDagTask>> taskList(@RequestBody ExtDagTask extDagTask) {

        List<ExtDagTask> list = extDagService.taskList(extDagTask);

        return new BaseResponse<>(list);
    }

    @PostMapping("tasks/add")
    public BaseResponse<Boolean> addTask(@Valid @RequestBody ExtDagTask extDagTask) {

        extDagService.addTask(extDagTask);

        return new BaseResponse<>(true);
    }

    @PostMapping("tasks/update")
    public BaseResponse<Boolean> updateTask(@Valid @RequestBody ExtDagTask extDagTask) {

        extDagService.updateTask(extDagTask);

        return new BaseResponse<>(true);
    }

    @DeleteMapping("tasks/delete")
    public BaseResponse<Boolean> updateTask(@RequestParam Long dagId, @RequestParam String taskId) {

        extDagService.deleteTask(dagId, taskId);

        return new BaseResponse<>(true);
    }

    @PostMapping("pushToGit")
    public BaseResponse<String> generatePy(@Valid @RequestBody GeneratedPyRq rq) {
        String url = pyGenerator.push(rq);

        return new BaseResponse<>(url);
    }


}
