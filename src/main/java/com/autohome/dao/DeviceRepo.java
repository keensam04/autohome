package com.autohome.dao;

import com.autohome.model.Device;
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
public class DeviceRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(DeviceRepo.class);

    public boolean addDevice(Device device) {

        String query = "INSERT INTO device(deviceName,dateOfPurchase,dateOfExpiry,isSwitch,powerRating,roomId,onboardedBy,dateOfOnboarding)" +
                "values(?,?,?,?,?,?,?,?)";

        int noOfRows = jdbcTemplate.update(query, device.getDeviceName(), device.getDateOfPurchase(), device.getDateOfExpiry(),
                device.getIsSwitch(), device.getPowerRating(), device.getRoomId(), device.getOnboardedBy(),
                device.getDateOfOnboarding().toInstant());

        return noOfRows > 0;
    }

    public Device getDevice(String deviceName) {
        String query = "SELECT * FROM device WHERE deviceName = ?";
        try {
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
                    d.setDateOfOnboarding(rs.getTimestamp("dateOfOnboarding"));

                    return d;
                }
            });
            return device;
        } catch (EmptyResultDataAccessException e) {
            log.warn("Device " + deviceName + " doesn't exist in table");
        }
        return null;
    }


}
