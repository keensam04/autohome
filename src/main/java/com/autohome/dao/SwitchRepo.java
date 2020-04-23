package com.autohome.dao;

import com.autohome.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class SwitchRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean switchOnOff(int roomId, int state, Device device) {
        String query = "INSERT INTO deviceStateLog (deviceId,state,date,user)" +
                "           values(?,?,?,?) ";

        int noOfRows = jdbcTemplate.update(query, device.getId(), state, device.getDateOfModification(), new StringBuilder("John Doe"));
        return noOfRows>0;
    }
}
