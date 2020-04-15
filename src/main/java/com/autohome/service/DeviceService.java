package com.autohome.service;

import com.autohome.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class DeviceService {
/*
    deviceName nvarchar(32),
    dateOfPurchase DATE,
    dateOfExpiry DATE,
    isSwitch boolean,
    powerRating float,
    roomId int,
    onboardedBy nvarchar(64),
    dateOfOnboarding TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP,*/

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean addDevice(Device device) {

        String query = "INSERT INTO device(deviceName,dateOfPurchase,dateOfExpiry,isSwitch,powerRating,roomId,onboardedBy,dateOfOnboarding)" +
                "values(?,?,?,?,?,?,?,?)";
        int update = jdbcTemplate.update(query, device.getDeviceName(), device.getDateOfPurchase(), device.getDateOfExpiry(),
                device.getIsSwitch(), device.getPowerRating(), device.getRoomId(), device.getOnboardedBy(),
                device.getDateOfOnboarding());

        return update > 0;

    }


    public Device getDevice(String deviceName) {
        return null;

    }

    public Device selectDevice(String deviceName){

        String query = "SELECT * FROM device WHERE deviceName = ?";
        Device device = jdbcTemplate.queryForObject(query, new Object[]{deviceName}, new RowMapper<Device>() {

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

    }


}
