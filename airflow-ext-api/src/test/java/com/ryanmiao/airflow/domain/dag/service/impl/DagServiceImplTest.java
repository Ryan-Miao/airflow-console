package com.ryanmiao.airflow.domain.dag.service.impl;

import static org.mockito.ArgumentMatchers.any;

import com.ryanmiao.airflow.domain.dag.mapper.DagMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DagServiceImplTest {

    @InjectMocks
    private DagServiceImpl sampleService;
    @Mock
    private DagMapper mapper;

    @Test
    public void list() {
//        when(mapper.list(any(Dag.class)))
//            .thenReturn(new ArrayList<>());
//
//        Dag dag = new Dag();
//        dag.setName("ryan");
//
//        List<Dag> list = sampleService.list(dag);
//        assertNotNull(list);
    }
    @Test
    public void listWithNameNull() {
//        when(mapper.list(any(Dag.class)))
//            .thenReturn(new ArrayList<>());
//
//        Dag dag = new Dag();
//
//        List<Dag> list = sampleService.list(dag);
//        assertNotNull(list);
    }

}