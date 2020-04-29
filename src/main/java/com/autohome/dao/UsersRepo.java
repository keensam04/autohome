package com.autohome.dao;

import com.autohome.model.Users;
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
public class UsersRepo {

    private static final Logger log = LoggerFactory.getLogger(UsersRepo.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Users getUsers(int id) {
        String query = "SELECT * FROM users WHERE id = ?";

        try {
            Users users = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Users>() {

                @Override
                public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Users u = new Users();
                    u.setEmail(rs.getString("email"));
                    u.setFirstName(rs.getString("firstName"));
                    u.setLastName(rs.getString("lastName"));
                    u.setInActive(rs.getBoolean("inActive"));
                    u.setPicture(rs.getString("picture"));
                    u.setRole(rs.getString("role"));
                    u.setId(rs.getInt("id"));

                    return u;
                }
            });
            return users;
        } catch (EmptyResultDataAccessException e) {
            log.warn("No User with id {} found", id);
            return null;
        }

    }



}
