package com.sqy.guap.repository;

import com.sqy.guap.domain.Teacher;
import com.sqy.guap.dto.TeacherDto;
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

    private static final String SQL_SELECT_TEACHER_BY_ID =
            "select * from teacher where teacher_id = :id";
    private static final String SQL_INSERT_TEACHER =
            "insert into teacher(establishment, name) values (:establishment, :name)";
    private static final String SQL_UPDATE_TEACHER =
            "update teacher set establishment = :establishment, name = :name where teacher_id = :id";
    private static final String SQL_DELETE_TEACHER =
            "delete from teacher where teacher_id = :id";

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

    public boolean updateTeacher(TeacherDto teacherDto) {
        try {
            if(getTeacherById(teacherDto.teacher_id()) == null) {
                return false;
            }
            npjTemplate.update(SQL_UPDATE_TEACHER, sqlParamsFromDto(teacherDto));
            return true;
        } catch (DataAccessException ex) {
            logger.error("Invoke updateTeacher({}) with exception.", teacherDto, ex);
        }
        return false;
    }

    public boolean removeTeacher(long id) {
        try {
            if(getTeacherById(id) == null) {
                return false;
            }
            npjTemplate.update(SQL_DELETE_TEACHER, new MapSqlParameterSource("id", id));
            return true;
        } catch (DataAccessException ex) {
            logger.error("Invoke removeTeacher({}) with exception.", id, ex);
        }
        return false;
    }

    public boolean createTeacher(TeacherDto teacherDto) {
        try {
            npjTemplate.update(SQL_INSERT_TEACHER, sqlParamsFromDto(teacherDto));
            return false;
        } catch (DataAccessException ex) {
            logger.error("Invoke createTeacher({}) with exception.", teacherDto, ex);
        }
        return true;
    }

    private static RowMapper<Teacher> getTeacherRowMapper() {
        return ((rs, rowNum) -> new Teacher(
                rs.getLong("teacher_id"),
                rs.getString("name"),
                rs.getString("establishment")
        ));
    }

    private static MapSqlParameterSource sqlParamsFromDto(TeacherDto dto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", dto.name());
        params.addValue("establishment", dto.establishment());
        params.addValue("id", dto.teacher_id());
        return params;
    }
}
