package com.sqy.guap.repository;

import com.sqy.guap.domain.Project;
import com.sqy.guap.domain.Pupil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectToPeopleResultSetExtractor implements ResultSetExtractor<List<Project>> {
    @Override
    public List<Project> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Project> projects = new ArrayList<>();
        long id = -1L;
        while (rs.next()) {
            if (rs.getLong("project_id") != id) {
                projects.add(new Project(
                        rs.getLong("project_id"),
                        rs.getString("project_theme"),
                        rs.getString("project_name"),
                        null,                           // todo files
                        rs.getLong("teacher_id"),
                        new ArrayList<>()
                ));
            }
            Pupil pupil = new Pupil(rs.getLong("pupil_id"), rs.getString("pupil_name"));
            projects.get(projects.size() - 1).students().add(pupil);
            id = rs.getLong("project_id");
        }
        return projects.size() == 0 ? null : projects;
    }
}
