package com.autohome.service;

import com.autohome.dao.DeviceRepo;
import com.autohome.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepo deviceRepo;

    public int addDevice(int roomId, Device device) {
        if (device != null && !isDevicePresent(roomId, device)) {
            return deviceRepo.addDevice(roomId, device);
        }
        return -1;
    }

    public Device getDevice(int roomId,int deviceId) {
      return deviceRepo.getDeviceById(roomId,deviceId);
    }

    private boolean isDevicePresent(int roomId, Device device) {
        Device deviceFromDb = deviceRepo.getDeviceByName(roomId,device.getDeviceName());
        return deviceFromDb != null && roomId == deviceFromDb.getRoomId() && device.getDeviceName().equalsIgnoreCase(deviceFromDb.getDeviceName());
    }

    public List<Device> getDevices(int roomId){
        return deviceRepo.getDevices(roomId);
    }

    public boolean updateDevice(int roomId, int id, Device device){
        if(!isDevicePresent(roomId,device))
            return deviceRepo.updateDevice(roomId,id,device);
        else
            return false;
    }

}
