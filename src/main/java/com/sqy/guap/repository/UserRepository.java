package com.sqy.guap.repository;

import com.sqy.guap.domain.User;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public static final String SQL_SELECT_BY_FROM_USERNAME = "select id, username from \"user\" where username = :username;";
    private final NamedParameterJdbcTemplate template;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.template = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Nullable
    public User getByUsername(String username) {
        try {
            return template.queryForObject(
                    SQL_SELECT_BY_FROM_USERNAME,
                    new MapSqlParameterSource("username", username),
                    new UserRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Invoke getByUsername({}) with exception.", username, e);
        }
        return null;
    }
}
