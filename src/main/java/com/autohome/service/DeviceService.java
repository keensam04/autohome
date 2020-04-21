package com.autohome.service;

import com.autohome.dao.DeviceRepo;
import com.autohome.dao.RoomRepo;
import com.autohome.model.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    DeviceRepo deviceRepo;

    @Autowired
    RoomRepo roomRepo;

    public static final Logger log = LoggerFactory.getLogger(DeviceService.class);

    public int addDevice(int roomId, Device device) {
        if (device != null && !isDevicePresent(roomId, device)) {
            log.info("Device with id {} has been added", device.getId());
            return deviceRepo.addDevice(roomId, device);
        }
        log.debug("Device with id {} was not added", device.getId());
        return -1;
    }

    public Device getDevice(int roomId, int deviceId) {
        return deviceRepo.getDeviceById(roomId, deviceId);
    }

    private boolean isDevicePresent(int roomId, Device device) {
        Device deviceFromDb = deviceRepo.getDeviceByName(roomId, device.getDeviceName());
        return deviceFromDb != null && roomId == deviceFromDb.getRoomId() && device.getDeviceName().equalsIgnoreCase(deviceFromDb.getDeviceName());
    }

    public List<Device> getDevices(int roomId) {
        return deviceRepo.getDevices(roomId);
    }

    public boolean updateDevice(int roomId, int id, Device device) {
        Device currentDevice = getDevice(roomId, id);
        if (currentDevice != null && !isDevicePresent(roomId, device)) {
            log.info("Device with id {} has been updated", device.getId());
            return deviceRepo.updateDevice(roomId, id, device);
        } else {
            log.debug("Device with id {} hasn't been updated", device.getId());
            return false;
        }
    }

    public Optional<Device> changeRoom(int roomId, int id, int newRoomId) {
        if (roomRepo.getRoom(newRoomId) != null) {
            int noOfRows = deviceRepo.changeRoom(roomId, id, newRoomId);
            if (noOfRows > 0)
                return Optional.ofNullable(deviceRepo.getDeviceById(newRoomId,id));
        }
        return Optional.empty();
    }

    public int offBoardDevice(int roomId, int id){
            return deviceRepo.offBoardDevice(roomId,id);

    }
}
