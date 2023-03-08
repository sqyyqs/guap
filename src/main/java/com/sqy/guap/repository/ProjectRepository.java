package com.sqy.guap.repository;

import com.sqy.guap.domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public class ProjectRepository {
    private final static Logger logger = LoggerFactory.getLogger(ProjectRepository.class);
    private final static String SQL_SELECT_PROJECT_BY_ID = "select * from project where projectId = :id";

    private final NamedParameterJdbcTemplate npjTemplate;

    public ProjectRepository(NamedParameterJdbcTemplate npjTemplate) {
        this.npjTemplate = npjTemplate;
    }

    public Project getProjectById(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

            return npjTemplate.queryForObject(SQL_SELECT_PROJECT_BY_ID, namedParameters, getProjectRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke existUserById({}).", id, ex);
        }
        return null;
    }

    private static RowMapper<Project> getProjectRowMapper() {
        return ((rs, rowNum) -> new Project(
                rs.getLong("projectId"),
                rs.getString("theme"),
                rs.getString("name"),
                null,
                rs.getLong("teacherId"),
                Collections.emptyList()
        ));
    }
}
