package com.autohome.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    Logger log = LoggerFactory.getLogger(HealthCheck.class);

    @RequestMapping(value = "/ping" ,method = RequestMethod.GET)
    public String showHealthCheck(){
        log.info("Logs here" );
        return "Pong";
    }
}
