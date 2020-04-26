package com.autohome.dao;

import com.autohome.model.DeviceStateLog;
import com.autohome.model.Switch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Repository
public class DeviceStateRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int switchOnOff(int roomId, int deviceId, Switch _switch, String userName) {
        String query = "INSERT INTO deviceStateLog (roomId,deviceId,state,date,user)" +
                "           values(?,?,?,?,?) ";
            return jdbcTemplate.update(query,roomId,deviceId,_switch.isOn(),Date.valueOf(LocalDate.now()),userName);
    }

    public DeviceStateLog isSwitchOnOff(int  deviceId) {
        String query = "SELECT * FROM deviceStateLog where id in (SELECT MAX(id) FROM deviceStateLog) AND deviceId = ?";
        DeviceStateLog deviceStateLog = jdbcTemplate.queryForObject(query, new Object[]{deviceId}, new RowMapper<DeviceStateLog>() {

            @Override
            public DeviceStateLog mapRow(ResultSet rs, int rowNum) throws SQLException {
                DeviceStateLog d = new DeviceStateLog();
                d.setState(rs.getInt("state"));
                d.setDate(rs.getDate("date"));
                d.setDeviceId(rs.getInt("deviceId"));
                d.setRoomId(rs.getInt("roomId"));
                d.setUser(rs.getString("user"));
                d.setTime(rs.getTimestamp("time").toInstant());
                return d;
            }
        });
        return deviceStateLog;
    }
}
