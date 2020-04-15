package com.autohome.service;

import com.autohome.dao.DeviceRepo;
import com.autohome.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    DeviceRepo deviceRepo;

    public boolean addDevice(Device device) {

        if (device != null && !isDevicePresent(device)) {
            return deviceRepo.addDevice(device);
        }
        return false;
    }

    public Device getDevice(String deviceName) {

        return deviceRepo.getDevice(deviceName);

    }

    private boolean isDevicePresent(Device device){
            return device.getDeviceName().equalsIgnoreCase(deviceRepo.getDevice(device.getDeviceName()).getDeviceName());
    }

}
