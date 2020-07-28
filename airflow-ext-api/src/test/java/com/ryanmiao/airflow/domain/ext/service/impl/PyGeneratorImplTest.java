package com.ryanmiao.airflow.domain.ext.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class PyGeneratorImplTest {

    @Test
    public void test() {
        String a = "poi/1_test.py";
        assertEquals("poi", a.split("/")[0]);
    }

}