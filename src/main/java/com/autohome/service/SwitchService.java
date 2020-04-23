package com.autohome.service;

import com.autohome.dao.SwitchRepo;
import com.autohome.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwitchService {

    @Autowired
    private SwitchRepo switchRepo;

    @Autowired
    DeviceService deviceService;

    public boolean switchOnOff(int roomId, int state, Device device){
        if(deviceService.getDevice(roomId,device.getId()) != null)
            return switchRepo.switchOnOff(roomId, state, device);
        else
            return false;
    }
}
