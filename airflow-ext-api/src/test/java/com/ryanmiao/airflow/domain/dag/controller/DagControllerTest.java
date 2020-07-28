package com.ryanmiao.airflow.domain.dag.controller;

import com.ryanmiao.airflow.domain.dag.service.IDagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DagControllerTest {

    @InjectMocks
    private DagController dagController;
    @Mock
    private IDagService sampleService;

    @Test
    public void list() {
//        when(sampleService.list(any(Dag.class)))
//            .thenReturn(new ArrayList<>());
//        BaseResponse<List<Dag>> response = dagController.list(new Dag());
//        assertEquals(0, response.getRetCode());
//        assertNotNull(response.getData());
    }
}