package com.autohome.controller;

import com.autohome.model.ErrorMessage;
import com.autohome.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author saman.tamkeen
 * 13/05/20
 */
@RestController
@RequestMapping(value = "/ping")
public class HealthCheckController {

    @Autowired
    PingService pingService;

    @RequestMapping(method = RequestMethod.GET)
    public String showHealthCheck(){
        return "pong";
    }

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public ResponseEntity<Object> pingDb(){
        if(pingService.pingDb())
            return ResponseEntity.ok("pong");
        else {
            ErrorMessage er = new ErrorMessage();
            er.setCause(Collections.singletonList("database not reachable"));
            er.setMsg("service down");
            er.setCode(505);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}