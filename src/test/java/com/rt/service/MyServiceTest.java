package com.rt.service;

import com.rt.Exception.MyException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyServiceTest {

    @Bean
    public MyService myMockService() throws Exception {
        MyService remoteService = Mockito.mock(MyService.class);
        Mockito.when(remoteService.mayFailedCall())
                .thenThrow(new MyException("My Exception 1"))
                .thenThrow(new MyException("My Exception 2"))
                .thenReturn(true);
        return remoteService;
    }

    @Autowired
    private MyService myService;

    @Test
    public void testRetry() throws MyException {
        Assert.assertTrue(myService.mayFailedCall());
        Assert.assertTrue(myService.getMayFailedCallTimes()==3);
    }

}
