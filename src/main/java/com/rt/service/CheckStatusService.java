package com.rt.service;

import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

@Service
public class CheckStatusService {

    private int failedCallTimes;

    @Retryable(value = HttpStatusCodeException.class, maxAttempts = 3,
            backoff = @Backoff(delay = 3000, maxDelay = 4000), exclude =
            HttpClientErrorException.class)
    public String checkStatus(String trackingNumber) {

        failedCallTimes++;

        if (failedCallTimes >= 3) {

            try {
                long startTime = System.currentTimeMillis();
                Thread.sleep(4000);

                long executionTime = (System.currentTimeMillis() - startTime) / 1000;

                if (executionTime > 3){
                    return "Failed";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Success";
        }

        // another microservice call to get status.
        //rest template call

        System.out.println("calling another service to get status!!");
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);

        // return "approved";
    }

    public int getMayFailedCallTimes() {
        return failedCallTimes;
    }


    @Recover
    public String recover(HttpServerErrorException exception) {
        return "Please try after some time!!";
    }
}
