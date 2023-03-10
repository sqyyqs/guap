package com.sqy.guap.repository;

import com.sqy.guap.domain.Teacher;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherRepository {
    private static final Logger logger = LoggerFactory.getLogger(TeacherRepository.class);

    private static final String SQL_SELECT_TEACHER_BY_ID = "select * from teacher where teacher_id = :id";

    private final NamedParameterJdbcTemplate npjTemplate;

    public TeacherRepository(NamedParameterJdbcTemplate npjTemplate) {
        this.npjTemplate = npjTemplate;
    }

    @Nullable
    public Teacher getTeacherById(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

            return npjTemplate.queryForObject(SQL_SELECT_TEACHER_BY_ID, namedParameters, getTeacherRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getTeacherById({}) with exception.", id, ex);
        }
        return null;
    }

    private static RowMapper<Teacher> getTeacherRowMapper() {
        return ((rs, rowNum) -> new Teacher(
                rs.getLong("teacher_id"),
                rs.getString("name"),
                rs.getString("establishment")
        ));
    }
}
