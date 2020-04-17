package com.autohome.dao;

import com.autohome.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

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

        String query = "SELECT * FROM room WHERE Id = ?";

        try {
            Room room = jdbcTemplate.queryForObject(query, new Object[]{Id}, new RowMapper<Room>() {
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
            log.warn("Room with Id {} not present",Id);
        }
        return null;
    }
    //Sql Injection;;
    public boolean updateRoom(Room room, int id){ // roomName = hall;delete * from device;
        String query = "UPDATE room SET roomName = ? WHERE id = ?";
        return jdbcTemplate.update(query,new Object[]{room.getRoomName(),id})>0;
    }

    public boolean deleteRoom(int id){
        String query = "DELETE FROM room WHERE id = ?";
        return jdbcTemplate.update(query,new Object[]{id})>0;
    }

    public List<Room> getRooms(){

        String query = "SELECT * FROM room";
        try {
            List<Room> listOfRoom = jdbcTemplate.query(query, new RowMapper<Room>() {

                @Override
                public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Room r = new Room();
                    r.setRoomName(rs.getString("roomName"));

                    return r;
                }
            });

            return listOfRoom;
        }
        catch (EmptyResultDataAccessException e){
            log.warn("No rooms found");
        }
        return null;
    }

}
