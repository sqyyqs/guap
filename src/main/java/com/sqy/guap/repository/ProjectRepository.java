package com.sqy.guap.repository;

import com.sqy.guap.domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);
    private static final String SQL_SELECT_PROJECT_BY_ID = "select * from project where project_id = :id";

    private final NamedParameterJdbcTemplate npjTemplate;

    public ProjectRepository(NamedParameterJdbcTemplate npjTemplate) {
        this.npjTemplate = npjTemplate;
    }

    public Project getProjectById(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

            return npjTemplate.queryForObject(SQL_SELECT_PROJECT_BY_ID, namedParameters, getProjectRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getProjectById({}) with exception.", id, ex);
        }
        return null;
    }

    private static RowMapper<Project> getProjectRowMapper() {
        return ((rs, rowNum) -> new Project(
                rs.getLong("project_id"),
                rs.getString("theme"),
                rs.getString("name"),
                null,
                rs.getLong("teacher_id")
        ));
    }
}
