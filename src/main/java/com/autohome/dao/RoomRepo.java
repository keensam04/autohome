package com.autohome.dao;

import com.autohome.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RoomRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addRoom(Room room) {
        String query = "INSERT INTO room(roomName) values(?)";
        int noOfRows = jdbcTemplate.update(query, room.getRoomName());
        return noOfRows > 0;
    }

    public Room getRoom(String roomName) {

        String query = "SELECT * FROM room WHERE roomName = ?";

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
    }

}
