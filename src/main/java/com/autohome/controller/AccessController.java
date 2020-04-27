package com.autohome.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/login")
public class AccessController {

    @RequestMapping(method = RequestMethod.GET)
    public void getUser(){
        Principal principal =(Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("User ");
    }


}
