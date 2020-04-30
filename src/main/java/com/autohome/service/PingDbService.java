package com.autohome.service;

import com.autohome.dao.PingDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PingDbService {

    @Autowired
    PingDbRepo pingDbRepo;

    public boolean pingDb(){
        return pingDbRepo.getPing();
    }
}
