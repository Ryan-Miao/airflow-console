package com.ryanmiao.airflow.security.service.impl;

import com.ryanmiao.airflow.security.config.UserClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserClient userClient;

    @Test
    public void logout() {
//        when(userClient.logOut(anyString()))
//            .then((Answer<Void>) invocation -> null);
//        authService.logout("token");
//        verify(userClient).logOut(anyString());
    }


}