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
    public void addRoomTest(){
        Room room = new Room();
        room.setId(1);
        room.setRoomName("hall");

        int roomAdded = roomService.addRoom(room);
        Assert.assertEquals(1, roomAdded);
    }
}
