package com.sqy.guap.repository;

import com.sqy.guap.domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);
    private static final String SQL_SELECT_PROJECT_BY_ID = """
            select p.project_id as project_id,
                   p.name as project_name,
                   p.theme as project_theme,
                   pupil.pupil_id as pupil_id,
                   pupil.name as pupil_name,
                   teacher_id
            from project_to_people
                     inner join pupil on project_to_people.pupil_id = pupil.pupil_id
                     inner join project p on p.project_id = project_to_people.project_id
            where p.project_id = :id;
            """;

    private final NamedParameterJdbcTemplate npjTemplate;

    public ProjectRepository(NamedParameterJdbcTemplate npjTemplate) {
        this.npjTemplate = npjTemplate;
    }

    public Project getProjectById(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
            List<Project> query = npjTemplate.query(SQL_SELECT_PROJECT_BY_ID, namedParameters, new ProjectToPeopleResultSetExtractor());
            return query == null ? null : query.get(0);
        } catch (DataAccessException ex) {
            logger.error("Invoke getProjectById({}) with exception.", id, ex);
        }
        return null;
    }


}
