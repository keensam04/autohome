package com.autohome.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @RequestMapping(value = "/ping" ,method = RequestMethod.GET)
    public String showHealthCheck(){
        return "Pong";
    }
}
