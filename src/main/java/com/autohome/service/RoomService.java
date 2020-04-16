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

    private boolean isRoomPresent(String roomName) {
        Room roomfromDb = roomRepo.getRoom(roomName);
        return roomfromDb!=null && roomfromDb.getRoomName().equalsIgnoreCase(roomName);
    }

    public Room getRoom(String roomName){
        return roomRepo.getRoom(roomName);
    }
    public boolean updateRoom(Room room, String roomName){
        if(isRoomPresent(room))
            return roomRepo.updateRoom(room,roomName);
        else
            return false;
    }

    public boolean deleteRoom(String roomName){
        if(isRoomPresent(roomName))
            return roomRepo.deleteRoom(roomName);
        else
            return false;
    }
}
