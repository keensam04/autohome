package com.autohome.dao;

import com.autohome.model.Device;
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
            Device device = jdbcTemplate.queryForObject(query, args, new RowMapper<Device>() {

                @Override
                public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
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

                    return d;
                }
            });
            return device;
        } catch (EmptyResultDataAccessException e) {
            log.warn("Device with args {} doesn't exist in table", args);
        }
        return null;
    }

    public List<Device> getDevices(int roomId) {
        String query = "SELECT * FROM device WHERE roomId = ?";
       try {
           List<Device> listOfDevices = jdbcTemplate.query(query, new Object[]{roomId}, new RowMapper<Device>() {

               @Override
               public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
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
                   return d;
               }
           });
           return listOfDevices;
       }
       catch (EmptyResultDataAccessException e){
           log.warn("No room with roomId{} found",roomId);
       }
       return null;
    }




}
