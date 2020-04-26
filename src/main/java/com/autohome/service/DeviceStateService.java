package com.autohome.service;

import com.autohome.dao.DeviceStateRepo;
import com.autohome.model.Switch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceStateService {

    @Autowired
    DeviceStateRepo deviceStateRepo;

    @Autowired
    DeviceService deviceService;

    public int switchOnOff(int roomId, int deviceId, Switch _switch,String userName){
        if(deviceService.getDevice(roomId,deviceId) != null)
            return deviceStateRepo.switchOnOff(roomId,deviceId,_switch,userName);
        else
            return -2;

    }
}
