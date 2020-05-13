package com.autohome.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author saman.tamkeen
 * 13/05/20
 */
@Repository
public class PingRepo {

    private static final Logger log = LoggerFactory.getLogger(PingRepo.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean getPing(){
        String query = "SELECT 1";
        try {
            jdbcTemplate.execute(query);
            return true;
        }
        catch (DataAccessException e){
            log.error("Database Down!!! -_-");
            return false;
        }
    }
}
