package com.autohome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @Autowired
    private Environment env;

    @RequestMapping(value = "/ping" ,method = RequestMethod.GET)
    public String showHealthCheck(){
        return "pong";
    }
}
