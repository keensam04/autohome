package com.autohome.dao;

import com.autohome.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RoomRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(RoomRepo.class);

    public boolean addRoom(Room room) {
        String query = "INSERT INTO room(roomName) values(?)";
        int noOfRows = jdbcTemplate.update(query, room.getRoomName());
        return noOfRows > 0;
    }

    public Room getRoom(String roomName) {

        String query = "SELECT * FROM room WHERE roomName = ?";

        try {
            Room room = jdbcTemplate.queryForObject(query, new Object[]{roomName}, new RowMapper<Room>() {
                @Override
                public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Room r = new Room();
                    r.setId(rs.getInt("id"));
                    r.setRoomName(rs.getString("roomName"));
                    return r;
                }
            });
            return room;
        } catch (EmptyResultDataAccessException e) {
            // this is a valid exception. indicates that roomName is not present in the table
            log.warn("Room "+roomName+" doesn't exist in table");
        }
        return null;
    }

    public boolean updateRoom(Room room, String roomName){
        String query = "UPDATE room SET roomName = "+ room.getRoomName() +" WHERE roomName = "+roomName;
        return jdbcTemplate.update(query)>0;
    }

    public boolean deleteRoom(String roomName){
        String query = "DELETE FROM room WHERE roomName = "+roomName;
        return jdbcTemplate.update(query)>0;
    }

}
