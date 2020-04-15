package com.autohome.service;

import com.autohome.dao.RoomRepo;
import com.autohome.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    public boolean addRoom(Room room){

        if (!isRoomPresent(room)) {
            return roomRepo.addRoom(room);
        }
        return false;
    }

    private boolean isRoomPresent(Room room) {
        Room roomFromDb = roomRepo.getRoom(room.getRoomName());
        return roomFromDb != null && roomFromDb.getRoomName().equalsIgnoreCase(room.getRoomName());
    }

    public Room getRoom(String roomName){
        return roomRepo.getRoom(roomName);
    }
}
