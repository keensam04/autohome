package com.autohome.service;

import com.autohome.config.TestConfig;
import com.autohome.model.Room;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Test
    public void testAddRoomWhenRoomIsNotPresent() {
        Room room = new Room();
        room.setRoomName("Bedroom");

        int roomAdded = roomService.addRoom(room);
        Assert.assertEquals(2, roomAdded);
    }

    @Test
    public void testAddRoomWhenRoomIsAlreadyPresent() {
        Room room = new Room();
        room.setRoomName("hall");

        int roomAdded = roomService.addRoom(room);
        Assert.assertEquals(-1, roomAdded);
    }

    @Test
    public void testAddRoomWhenNullIsTriedToAdd() {
        int addRoom = roomService.addRoom(null);
        Assert.assertEquals(-1, addRoom);
    }

    @Test
    public void testAddRoomWhenNoRoomsNameIsGiven() {
        int addRoom = roomService.addRoom(new Room());
        Assert.assertEquals(-1, addRoom);
    }

    @Test
    public void testGetRoomWhenRoomIsPresent() {
        int roomId = 1;
        Room room = new Room();
        room.setId(1);
        room.setRoomName("hall");

        Room returnedRoom = roomService.getRoom(roomId);
        Assert.assertEquals(room, returnedRoom);
    }

    @Test
    public void testGetRoomWhenRoomIsAbsent(){
        int roomId = 2;

        Room returnedRoom = roomService.getRoom(roomId);
        Assert.assertEquals(null,returnedRoom);
    }

    @Test
    public void testUpdateRoomWhenRoomIsPresent(){
        int roomId = 1;
        Room roomToUpdate = new Room();
        roomToUpdate.setId(1);
        roomToUpdate.setRoomName("bedroom");

        boolean isUpdated = roomService.updateRoom(roomToUpdate, roomId);
        Assert.assertEquals(true,isUpdated);
    }

    @Test
    public void testUpdateRoomWhenRoomIsAbsent(){
        int roomId = 1;
        Room roomToUpdate = new Room();
        roomToUpdate.setId(3);
        roomToUpdate.setRoomName("bedroom");

        boolean isUpdated = roomService.updateRoom(roomToUpdate, roomId);
        Assert.assertEquals(false,isUpdated);
    }

    @Test
    public void testUpdateWhenUpdatedRoomNameIsAlreadyPresent(){
        int roomId = 1;
        Room roomToUpdate = new Room();
        roomToUpdate.setId(1);
        roomToUpdate.setRoomName("bedroom");

        boolean isUpdated = roomService.updateRoom(roomToUpdate,roomId);
        Assert.assertEquals(false,isUpdated);

    }
}
