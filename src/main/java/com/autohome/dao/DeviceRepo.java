package com.autohome.dao;

import com.autohome.model.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class DeviceRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(DeviceRepo.class);

    public int addDevice(int roomId, Device device) {
        String query = "INSERT INTO device(deviceName,dateOfPurchase,dateOfExpiry,isSwitch,powerRating,roomId,onboardedBy)" +
                "values(?,?,?,?,?,?,?)";
        KeyHolder deviceKeyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, device.getDeviceName());
                statement.setDate(2, new Date(device.getDateOfPurchase().getTime()));
                statement.setDate(3, new Date(device.getDateOfExpiry().getTime()));
                statement.setBoolean(4, device.getIsSwitch());
                statement.setFloat(5, device.getPowerRating());
                statement.setInt(6, roomId);
                statement.setString(7, device.getOnboardedBy());
                return statement;
            }
        };
        jdbcTemplate.update(preparedStatementCreator, deviceKeyHolder);
        return deviceKeyHolder.getKey() == null ? 0 : deviceKeyHolder.getKey().intValue();
    }

    public Device getDeviceById(int roomId, int deviceId) {
        String query = "SELECT * FROM device WHERE roomId = ? AND id = ?";
        return getDevice(roomId, new Object[]{roomId, deviceId}, query);
    }

    public Device getDeviceByName(int roomId, String deviceName) {
        String query = "SELECT * FROM device WHERE roomId = ? AND deviceName = ?";
        return getDevice(roomId, new Object[]{roomId, deviceName}, query);
    }

    private Device getDevice(int roomId, Object[] args, String query) {
        try {
            Device device = jdbcTemplate.queryForObject(query, args, (rs, rowNum) -> {
                return rowNum(rs);
            });
            log.info("Device with id {} has been found",device.getId());
            return device;
        } catch (EmptyResultDataAccessException e) {
            log.warn("Device with args {} doesn't exist in table", args);
        }
        return null;
    }

    public List<Device> getDevices(int roomId) {
        String query = "SELECT * FROM device WHERE roomId = ?";
       try {
           List<Device> listOfDevices = jdbcTemplate.query(query, new Object[]{roomId}, (rs, rowNum) -> {
               return rowNum(rs);
           });
           log.info("Devices has been returned");
           return listOfDevices;
       }
       catch (EmptyResultDataAccessException e){
           log.warn("No room with roomId{} found",roomId);
       }
       return null;
    }

    private Device rowNum(ResultSet rs) throws SQLException {
        Device d = new Device();
        d.setId(rs.getInt("id"));
        d.setDeviceName(rs.getString("deviceName"));
        d.setDateOfPurchase(rs.getDate("dateOfPurchase"));
        d.setDateOfExpiry(rs.getDate("dateOfExpiry"));
        d.setIsSwitch(rs.getBoolean("isSwitch"));
        d.setPowerRating(rs.getFloat("powerRating"));
        d.setRoomId(rs.getInt("roomId"));
        d.setOnboardedBy(rs.getString("onboardedBy"));
        d.setDateOfOnboarding(rs.getTimestamp("dateOfOnboarding").toInstant());
        d.setIsSwitch(rs.getBoolean("isActive"));
        return d;
    }

    public boolean updateDevice(int roomId, int id, Device device) {
        String query = "UPDATE device" +
                "           SET deviceName = ?," +
                "                dateOfPurchase = ?," +
                "                dateOfExpiry = ?," +
                "                isSwitch = ?," +
                "                powerRating = ?," +
                "                onboardedBy = ?," +
                "                dateOfModification = ?" +
                "       WHERE roomId = ? AND id = ?";

        Object[] args = new Object[]{
                device.getDeviceName(),
                new Date(device.getDateOfPurchase().getTime()),
                new Date(device.getDateOfExpiry().getTime()),
                device.getIsSwitch(),
                device.getPowerRating(),
                device.getOnboardedBy(),
                Timestamp.from(device.getDateOfModification()),
                roomId,
                id};
        int noOfRows = jdbcTemplate.update(query, args);
        return true;

    }

    public int changeRoom(int roomId, int id,int newRoomId){
        String query = "UPDATE device SET roomId = ? WHERE id = ? AND roomId = ?";
        try{
        int noOfRows = jdbcTemplate.update(query, newRoomId, id, roomId);
        return noOfRows;
        }
        catch (EmptyResultDataAccessException e){
            log.warn("Device with roomId {} or deviceId {} not found",roomId,id);
            return -1;
        }
    }

    public int offBoardDevice(int roomId, int id){
        String query = "UPDATE device SET roomId = 0,isActive = false WHERE roomId = ? AND id = ?";
        try {
            int noOfRows = jdbcTemplate.update(query, roomId, id);
            return noOfRows;
        }
        catch(EmptyResultDataAccessException e){
            log.error("Device with roomId {} or id {} not found",roomId,id);
        }
        return -1;
    }

//This method can be used while deleting/Off Boarding all the devices from a room.
    public int offBoardDevices(int roomId ){
        List<Device> devices = getDevices(roomId);
        StringBuilder  query = new StringBuilder("UPDATE device SET roomId = 0,isActive = false, offBoardedBY=? WHERE roomId = ? AND id IN (");
        List<Object> ids = new ArrayList<>();
        ids.add("John Doe");
        ids.add(roomId);

        int i = 1;
        Iterator<Device> iterator = devices.iterator();
        while ((iterator.hasNext())){
            ids.add(iterator.next().getId());
            query.append('?');
            if (i != devices.size())
                query.append(',');
            i++;
        }
        query.append(")");

        try {
            int noOfRows = jdbcTemplate.update(query.toString(), ids.toArray());
            return noOfRows;
        }
        catch(EmptyResultDataAccessException e){
            log.error("Room with roomId {} not found ",roomId);
        }
        return -1;
    }

/*

    public boolean deleteDevice(int roomId, int id){
        String query = "DELETE FROM device WHERE roomId = ? AND id = ?";
        return jdbcTemplate.update(query, new Object[]{roomId,id})>0;
    }
*/


}
