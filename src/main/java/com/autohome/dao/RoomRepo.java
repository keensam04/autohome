package com.autohome.dao;

import com.autohome.model.Device;
import com.autohome.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class RoomRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(RoomRepo.class);

    public int addRoom(Room room) {
        String query = "INSERT INTO room(roomName) values(?)";

        KeyHolder roomKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1,room.getRoomName());
                return statement;
            }
        },roomKeyHolder) ;

        return  roomKeyHolder.getKey().intValue();
    }

    public Room getRoom(int Id) {

        String query = "SELECT room.id AS roomId, room.roomName, device.id AS deviceId, device.deviceName " +
                "                FROM room " +
                "                LEFT JOIN device ON " +
                "                room.id = device.roomId " +
                "                WHERE room.id = ?";

        try {
            List<Room> rooms = jdbcTemplate.query(query, new Object[]{Id}, roomMapper());
            if (rooms != null && rooms.size() == 1) {
                log.info("Room with size has been found", rooms.size());
                return rooms.get(0);
            }
            else{
                log.debug("No room has been found ");
                return null;
            }
        } catch (EmptyResultDataAccessException e) {
            // this is a valid exception. indicates that roomName is not present in the table
            log.warn("Room with Id {} not present",Id);
        }
        return null;
    }
    //Sql Injection;;
    public boolean updateRoom(Room room, int id){ // roomName = hall;delete * from device;
        String query = "UPDATE room SET roomName = ? WHERE id = ?";
        return jdbcTemplate.update(query, room.getRoomName(), id)>0;
    }

/*    public boolean deleteRoom(int id){
        String query = "DELETE FROM room WHERE id = ?";
        return jdbcTemplate.update(query, id)>0;
    }*/

    public List<Room> getRooms(){

        String query = "SELECT room.id AS roomId, room.roomName, device.id AS deviceId, device.deviceName " +
                "FROM room " +
                "LEFT JOIN device ON " +
                "room.id = device.roomId";
        try {
            return jdbcTemplate.query(query, roomMapper());
        }
        catch (EmptyResultDataAccessException e){
            log.warn("No rooms found");
        }
        return null;
    }

    private Room mapRoom(ResultSet rs, int rowNum) throws SQLException {
        Room r = new Room();
        r.setId(rs.getInt("id"));
        r.setRoomName(rs.getString("roomName"));

        return r;
    }

    private ResultSetExtractor<List<Room>> roomMapper(){
        return new ResultSetExtractor<List<Room>>() {
            @Override
            public List<Room> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<Integer, Room> rooms= new HashMap<>();
                while(rs.next()){
                    Integer roomId = rs.getInt("roomId");
                    // try to get room from map; if map does not contain room then create a new room and add it to the map
                    Room room;
                    if (rooms.containsKey(roomId))
                        room = rooms.get(roomId);
                    else {
                        room = new Room();
                        room.setId(roomId);
                        room.setRoomName(rs.getString("roomName"));
                        rooms.put(roomId, room);
                    }
//                    String roomName = rs.getString("roomName");
//                    Room room = rooms.computeIfAbsent(roomId, rId -> {
//                        Room r = new Room();
//                        r.setId(rId);
//                        r.setRoomName(roomName);
//                        return r;
//                    });
                    int deviceId = rs.getInt("deviceId");
                    if(deviceId != 0) {
                        Device device = new Device();
                        device.setId(rs.getInt("deviceId"));
                        device.setDeviceName(rs.getString("deviceName"));
                        room.getDevices().add(device);
                    }
                }
                return new ArrayList<>(rooms.values());
            }
        };
    }

}
