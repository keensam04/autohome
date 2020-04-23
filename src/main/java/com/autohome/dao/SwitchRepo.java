package com.autohome.dao;

import com.autohome.model.Device;
import com.autohome.model.DeviceStateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

//  deviceId int NOT NULL,
//          state TINYINT,
//          time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
//          date DATE NOT NULL,
//          user nvarchar(64),

public class SwitchRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DeviceStateLog switchOnOff(int roomId, Device device) {
        String query = "INSERT INTO deviceStateLog (deviceId,state,time,date,user)" +
                "           values(?,?,?,?) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1,device.getId());

                return null;
            }
        }
    }
}
