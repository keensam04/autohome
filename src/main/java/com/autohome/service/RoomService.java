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

        if (!isRoomPresent(room.getId())) {
            log.info("Room with id {} has been added", room.getId());
            return roomRepo.addRoom(room);
        }
        log.debug("Room with id {} hasn't been added", room.getId());
        return -1;
    }

    private boolean isRoomPresent(int id) {
        Room roomFromDb = roomRepo.getRoom(id);
        return roomFromDb != null;
    }

    public Room getRoom(int id) {
        return roomRepo.getRoom(id);
    }

    public boolean updateRoom(Room room, int id) {
        if (isRoomPresent(id)) {
            log.info("Room with id {} has not been updated", room.getId());
            return roomRepo.updateRoom(room, id);
        } else {
            log.debug("Room with id {} has not been updated", room.getId());
            return false;
        }

    }

    public boolean deleteRoom(int id) {
        if (isRoomPresent(id)) {
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
