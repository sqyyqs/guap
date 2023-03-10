package com.sqy.guap.repository;

import com.sqy.guap.domain.Project;
import com.sqy.guap.dto.ProjectDto;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ProjectRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);

    private static final String SQL_SELECT_PROJECT_BY_ID =
            "select project_id, theme, name, teacher_id from project where project_id = :id";
    private static final String SQL_SELECT_PROJECTS_BY_STUDENT_ID = """
            select pts.project_id as project_id,
                   p.name         as name,
                   p.theme        as theme,
                   p.teacher_id   as teacher_id
            from project_to_student pts
                     inner join project p on p.project_id = pts.project_id
            where pts.student_id = :id""";
    private static final String SQL_SELECT_PROJECTS_BY_TEACHER_ID =
            "select project_id, theme, name, teacher_id from project where teacher_id = :id";
    private static final String SQL_SELECT_ALL_PROJECTS =
            "select project_id, theme, name, teacher_id from project";
    private static final String SQL_UPDATE_PROJECT =
            "update project set theme = :theme, name = :name, teacher_id = :teacher_id where project_id = :id";
    private static final String SQL_DELETE_PROJECT =
            "delete from project where project_id = :id";
    private static final String SQL_INSERT_PROJECT =
            "insert into project(theme, name, teacher_id) values (:theme, :name, :teacher_id);";

    private final NamedParameterJdbcTemplate npjTemplate;

    public ProjectRepository(NamedParameterJdbcTemplate npjTemplate) {
        this.npjTemplate = npjTemplate;
    }

    @Nullable
    public Project getProjectById(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
            return npjTemplate.queryForObject(
                    SQL_SELECT_PROJECT_BY_ID, namedParameters, projectRowMapper()
            );
        } catch (DataAccessException ex) {
            logger.error("Invoke getProjectById({}) with exception.", id, ex);
        }
        return null;
    }

    @Nullable
    public Collection<Project> getProjectsByStudentId(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
            return npjTemplate.query(
                    SQL_SELECT_PROJECTS_BY_STUDENT_ID, namedParameters, projectRowMapper()
            );
        } catch (DataAccessException ex) {
            logger.error("Invoke getProjectsByStudentId({}) with exception.", id, ex);
        }
        return null;
    }

    @Nullable
    public Collection<Project> getProjectsByTeacherId(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
            return npjTemplate.query(
                    SQL_SELECT_PROJECTS_BY_TEACHER_ID, namedParameters, projectRowMapper()
            );
        } catch (DataAccessException ex) {
            logger.error("Invoke getProjectsByTeacherId({}) with exception.", id, ex);
        }
        return null;
    }

    @Nullable
    public Collection<Project> getAllProjects() {
        try {
            return npjTemplate.query(SQL_SELECT_ALL_PROJECTS, projectRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke removeProject() with exception.", ex);
        }
        return null;
    }

    public boolean updateProject(ProjectDto projectDto) {
        try {
            if (getProjectById(projectDto.projectId()) == null) {
                return false;
            }
            npjTemplate.update(SQL_UPDATE_PROJECT, sqlParametersFromDto(projectDto));
            return true;
        } catch (DataAccessException ex) {
            logger.error("Invoke updateProject({}) with exception.", projectDto, ex);
        }
        return false;
    }

    public boolean createProject(ProjectDto projectDto) {
        try {
            npjTemplate.update(SQL_INSERT_PROJECT, sqlParametersFromDto(projectDto));
            return true;
        } catch (DataAccessException ex) {
            logger.error("Invoke createProject({}) with exception.", projectDto, ex);
        }
        return false;
    }


    public boolean removeProject(long id) {
        try {
            if (getProjectById(id) == null) {
                return false;
            }
            npjTemplate.update(SQL_DELETE_PROJECT, new MapSqlParameterSource("id", id));
            return true;
        } catch (DataAccessException ex) {
            logger.error("Invoke removeProject({}) with exception.", id, ex);
        }
        return false;
    }

    private static RowMapper<Project> projectRowMapper() {
        return ((rs, rowNum) -> new Project(
                rs.getLong("project_id"),
                rs.getString("theme"),
                rs.getString("name"),
                rs.getLong("teacher_id")
        ));
    }

    private static MapSqlParameterSource sqlParametersFromDto(ProjectDto projectDto) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("theme", projectDto.theme());
        namedParameters.addValue("name", projectDto.name());
        namedParameters.addValue("teacher_id", projectDto.teacherId());
        namedParameters.addValue("id", projectDto.projectId());
        return namedParameters;
    }
}

