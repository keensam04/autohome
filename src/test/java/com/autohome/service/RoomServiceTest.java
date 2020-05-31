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
    public void testAddRoomWhenRoomIsNotPresent(){
        Room room = new Room();
        room.setRoomName("Bedroom");

        int roomAdded = roomService.addRoom(room);
        Assert.assertEquals(2, roomAdded);
    }

    @Test
    public void testAddRoomWhenRoomIsAlreadyPresent(){
        Room room = new Room();
        room.setRoomName("hall");

        int roomAdded = roomService.addRoom(room);
        Assert.assertEquals(-1,roomAdded);
    }

    @Test
    public void testGetRoomWhenRoomIsPresent(){
        int roomId = 1;
        Room room = new Room();
        room.setRoomName("hall");
        room.setId(1);

        Room returnedRoom = roomService.getRoom(roomId);
        Assert.assertEquals(room,returnedRoom);
    }
}
