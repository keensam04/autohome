package com.autohome.service;

import com.autohome.dao.PingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author saman.tamkeen
 * 13/05/20
 */
@Service
public class PingService {

    @Autowired
    PingRepo pingRepo;

    public boolean pingDb(){
        return pingRepo.getPing();
    }
}
