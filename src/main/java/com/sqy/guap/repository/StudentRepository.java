package com.sqy.guap.repository;

import com.sqy.guap.domain.Student;
import com.sqy.guap.dto.StudentDto;
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
public class StudentRepository {
    private static final Logger logger = LoggerFactory.getLogger(StudentRepository.class);

    private static final String SQL_SELECT_STUDENT_BY_ID =
            "select student_id, name from student where student_id = :id";
    private static final String SQL_SELECT_STUDENTS_BY_PROJECT_ID = """
            select pts.student_id as student_id,
                   s.name         as name
            from project_to_student pts
                     inner join student s on s.student_id = pts.student_id
            where pts.project_id = :project_id""";
    private static final String SQL_INSERT_STUDENT =
            "insert into student(name) values (:name)";
    private static final String SQL_UPDATE_STUDENT =
            "update student set name = :name where student_id = :id";
    private static final String SQL_DELETE_STUDENT =
            "delete from student where student_id = :id";

    private final NamedParameterJdbcTemplate npjTemplate;

    public StudentRepository(NamedParameterJdbcTemplate npjTemplate) {
        this.npjTemplate = npjTemplate;
    }

    @Nullable
    public Student getStudentById(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
            return npjTemplate.queryForObject(SQL_SELECT_STUDENT_BY_ID, namedParameters, getStudentRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getStudentById({}) with exception.", id, ex);
        }
        return null;
    }

    @Nullable
    public Collection<Student> getStudentsByProjectId(long projectId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("project_id", projectId);
            return npjTemplate.query(SQL_SELECT_STUDENTS_BY_PROJECT_ID, namedParameters, getStudentRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getStudentsByProjectId({}) with exception.", projectId, ex);
        }
        return null;
    }

    public boolean createStudent(StudentDto studentDto) {
        try {
            npjTemplate.update(SQL_INSERT_STUDENT, sqlParametersFromDto(studentDto));
            return true;
        } catch (DataAccessException ex) {
            logger.error("Invoke createStudent({}) with exception.", studentDto, ex);
        }
        return false;
    }

    public boolean updateStudent(StudentDto studentDto) {
        try {
            if (getStudentById(studentDto.id()) == null) {
                return false;
            }
            npjTemplate.update(SQL_UPDATE_STUDENT, sqlParametersFromDto(studentDto));
            return true;
        } catch (DataAccessException ex) {
            logger.error("Invoke updateStudent({}) with exception.", studentDto, ex);
        }
        return false;
    }

    public boolean removeStudent(long id) {
        try {
            if (getStudentById(id) == null) {
                return false;
            }
            npjTemplate.update(SQL_DELETE_STUDENT, new MapSqlParameterSource("id", id));
            return true;
        } catch (DataAccessException ex) {
            logger.error("Invoke removeStudent({}) with exception.", id);
        }
        return false;
    }

    private static RowMapper<Student> getStudentRowMapper() {
        return ((rs, rowNum) -> new Student(
                rs.getLong("student_id"),
                rs.getString("name")
        ));
    }

    private static MapSqlParameterSource sqlParametersFromDto(StudentDto studentDto) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", studentDto.name());
        namedParameters.addValue("id", studentDto.id());
        return namedParameters;
    }
}
