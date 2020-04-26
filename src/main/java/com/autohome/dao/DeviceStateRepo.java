package com.autohome.dao;

import com.autohome.model.Switch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;

@Repository
public class DeviceStateRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int switchOnOff(int roomId, int deviceId, Switch _switch, String userName) {
        String query = "INSERT INTO deviceStateLog (deviceId,state,date,user)" +
                "           values(?,?,?,?) ";
            return jdbcTemplate.update(query,deviceId,_switch.isOff(),Date.valueOf(LocalDate.now()),userName);
    }
}
