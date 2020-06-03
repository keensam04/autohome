package com.autohome.service;

import com.autohome.config.TestConfig;
import com.autohome.model.Room;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void removeRowsFromDb() {

        String query = "INSERT into room (id,roomName) " +
                "values(1, 'hall');" +
                "INSERT into room (id,roomName)" +
                "    Values (2,'bedroom');";
        jdbcTemplate.execute(query);
    }

    @After
    public void addRowsInDb() {
        String query = "DELETE FROM room;";
        jdbcTemplate.execute(query);
    }

    @Test
    public void testAddRoomWhenRoomIsNotPresent() {
        Room room = new Room();
        room.setRoomName("Bedroom");

        int roomAdded = roomService.addRoom(room);
        Assert.assertEquals(3, roomAdded);
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
    public void testAddRoomWhenRoomNameIsNull() {
        int addRoom = roomService.addRoom(new Room());
        Assert.assertEquals(-1, addRoom);
    }

    @Test
    public void testAddRoomWhenRoomNameIsEmptyString() {
        Room room = new Room();
        room.setId(3);
        room.setRoomName("");

        int roomAdded = roomService.addRoom(room);
        Assert.assertEquals(-1, roomAdded);
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
    public void testGetRoomWhenRoomIsAbsent() {
        int roomId = 3;

        Room returnedRoom = roomService.getRoom(roomId);
        Assert.assertEquals(null, returnedRoom);
    }

    @Test
    public void testUpdateRoomWhenRoomIsPresent() {
        int roomId = 2;
        Room roomToUpdate = new Room();
        roomToUpdate.setRoomName("toilet");

        boolean isUpdated = roomService.updateRoom(roomToUpdate, roomId);
        Assert.assertEquals(true, isUpdated);
    }

    @Test
    public void testUpdateRoomWhenRoomIsAbsent() {
        int roomId = 4;
        Room roomToUpdate = new Room();
        roomToUpdate.setRoomName("bedroom");

        boolean isUpdated = roomService.updateRoom(roomToUpdate, roomId);
        Assert.assertEquals(false, isUpdated);
    }

    @Test
    public void testUpdateWhenUpdatedRoomNameIsAlreadyPresent() {
        int roomId = 1;
        Room roomToUpdate = new Room();
        roomToUpdate.setId(1);
        roomToUpdate.setRoomName("hall");

        boolean isUpdated = roomService.updateRoom(roomToUpdate, roomId);
        Assert.assertEquals(false, isUpdated);
    }

    @Test
    public void testGetRoomsWhenRoomsArePresent() {
        List<Room> expectedRooms = new ArrayList<Room>();
        Room room1 = new Room();
        Room room2 = new Room();

        room1.setId(1);
        room1.setRoomName("hall");
        room2.setId(2);
        room2.setRoomName("bedroom");

        expectedRooms.add(room1);
        expectedRooms.add(room2);

        List<Room> roomReceivedFromDb = roomService.getRooms();

        Assert.assertEquals(expectedRooms, roomReceivedFromDb);
    }

    @Test
    public void testDeleteRoomWhenRoomIsPresent() {
        Room roomInDb = new Room();
        roomInDb.setId(1);
        roomInDb.setRoomName("hall");

        boolean deleted = roomService.deleteRoom(roomInDb.getId());
        Assert.assertEquals(true, deleted);

    }

    @Test
    public void testDeleteRoomWhenRoomIsAbsent() {
        Room roomInDb = new Room();
        roomInDb.setId(3);
        roomInDb.setRoomName("hall");

        boolean deleted = roomService.deleteRoom(roomInDb.getId());
        Assert.assertEquals(false, deleted);
    }

    @Test
    public void testDeleteRoomAndDeviceIdIsZero() {
        String query = "SELECT FROM room where id = 0";
        jdbcTemplate.query(query, new ResultSetExtractor<List<Room>>(){
            @Override
            public List<Room> extractData(ResultSet rs) throws SQLException, DataAccessException {
                return null;
            }
        });
    }
}
