package com.autohome.service;

import com.autohome.dao.DeviceStateRepo;
import com.autohome.model.Device;
import com.autohome.model.DeviceStateLog;
import com.autohome.model.Switch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceStateService {

    @Autowired
    DeviceStateRepo deviceStateRepo;

    @Autowired
    DeviceService deviceService;

    public int switchOnOff(int roomId, int deviceId, Switch _switch,String userName) {
        Device device = deviceService.getDevice(roomId,deviceId);
        if(device == null)
            return -2;

        DeviceStateLog currentState = deviceStateRepo.isSwitchOnOff(deviceId);
        if (currentState == null)
            return deviceStateRepo.switchOnOff(roomId, deviceId, _switch, userName);

        boolean isDeviceOff = currentState.getState() == 0;
        if (isDeviceOff == _switch.isOn())
            return deviceStateRepo.switchOnOff(roomId, deviceId, _switch, userName);

        return -1;
    }
}
