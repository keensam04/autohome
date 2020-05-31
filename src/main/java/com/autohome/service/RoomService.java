package com.autohome.service;

import com.autohome.dao.RoomRepo;
import com.autohome.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private DeviceService deviceService;

    private static final Logger log = LoggerFactory.getLogger(RoomService.class);

    public int addRoom(Room room) {

        if (!isRoomPresentByName(room.getRoomName())) {
            log.info("Room with name {} has been added", room.getRoomName());
            return roomRepo.addRoom(room);
        }
        log.debug("Room with id {} hasn't been added", room.getId());
        return -1;
    }

    private boolean isRoomPresentByName(String roomName){
        Room roomFromBb = roomRepo.getRoomByName(roomName);
        return roomFromBb != null;
    }

    private boolean isRoomPresentById(int id) {
        Room roomFromDb = roomRepo.getRoomById(id);
        return roomFromDb != null;
    }

    public Room getRoom(int id) {
        return roomRepo.getRoomById(id);
    }

    public boolean updateRoom(Room room, int id) {
        if (isRoomPresentById(id)) {
            log.info("Room with id {} has not been updated", room.getId());
            return roomRepo.updateRoom(room, id);
        } else {
            log.debug("Room with id {} has not been updated", room.getId());
            return false;
        }

    }

    public boolean deleteRoom(int id) {
        if (isRoomPresentById(id)) {
            deviceService.offBoardDevices(id);
            return roomRepo.deleteRoom(id);
        } else {
            log.debug("Room with id {} was not deleted ", id);
            return false;
        }
    }

    public List<Room> getRooms() {
        return roomRepo.getRooms();
    }
}
