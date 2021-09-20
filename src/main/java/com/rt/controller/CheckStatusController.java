package com.rt.controller;

import com.rt.service.CheckStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckStatusController {

    @Autowired
    private CheckStatusService checkStatusService;

    @GetMapping("/check")
    public String checkStatus(@RequestParam(name = "tid") String trackingNumber) {
        return checkStatusService.checkStatus(trackingNumber);
    }
}
