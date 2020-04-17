package com.autohome.service;

import com.autohome.dao.RoomRepo;
import com.autohome.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    public int addRoom(Room room) {

        if (!isRoomPresent(room.getId())) {
            return roomRepo.addRoom(room);
        }
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
        if (isRoomPresent(id))
            return roomRepo.updateRoom(room, id);
        else
            return false;
    }

    public boolean deleteRoom(int id) {
        if (isRoomPresent(id))
            return roomRepo.deleteRoom(id);
        else
            return false;
    }

    public List<Room> getRooms(){
        return roomRepo.getRooms();
    }
}
