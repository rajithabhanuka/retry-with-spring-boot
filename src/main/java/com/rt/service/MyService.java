package com.rt.service;

import com.rt.Exception.MyException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    private int mayFailedCallTimes;

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public boolean mayFailedCall() throws MyException {
        mayFailedCallTimes++;
        System.out.println("mayFailedCallTimes = " + mayFailedCallTimes);
        if (mayFailedCallTimes >= 3) {
            return true;
        }
        throw new MyException("exception " + mayFailedCallTimes);
    }

    public int getMayFailedCallTimes() {
        return mayFailedCallTimes;
    }
}
