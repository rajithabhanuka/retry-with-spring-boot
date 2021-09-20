package com.rt.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpServerErrorException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CheckStatusServiceTest {

    @Bean
    public CheckStatusService checkStatus() throws HttpServerErrorException {
        CheckStatusService remoteService = Mockito.mock(CheckStatusService.class);
        Mockito.when(remoteService.checkStatus("1"))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .thenReturn("Done");
        return remoteService;
    }

    @Autowired
    private CheckStatusService checkStatusService;

//    @Test
//    public void testRetry() throws HttpServerErrorException {
//
//        Assert.assertEquals("Success", checkStatusService.checkStatus("1"));
//        Assert.assertTrue(checkStatusService.getMayFailedCallTimes() == 3);
//    }

    @Test
    public void testLastRetryCheckDelayLongerThanThreeSecond() throws HttpServerErrorException {

        Assert.assertEquals("Failed", checkStatusService.checkStatus("1"));
        Assert.assertTrue(checkStatusService.getMayFailedCallTimes() == 3);
    }


}
