package com.autohome.service;

import com.autohome.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class RoomService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addRoom(Room room){
        String query = "INSERT INTO room(roomName) values(?)";
        int noOfRows = jdbcTemplate.update(query, room.getRoomName());

        return noOfRows>0;
    }

    public Room getRoom(String roomName){

        String query = "SELECT * FROM room where roomName = ?";
//        Room room = jdbcTemplate.queryForObject(query, new Object[]{roomName},new BeanPropertyRowMapper<>(Room.class));

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
