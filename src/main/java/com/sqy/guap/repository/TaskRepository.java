package com.sqy.guap.repository;

import com.sqy.guap.domain.Task;
import com.sqy.guap.dto.TaskDto;
import com.sqy.guap.dto.UpdateTaskStatusDto;
import com.sqy.guap.dto.UpdateTaskTypeDto;
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
public class TaskRepository {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    private static final String SQL_SELECT_TASK_BY_ID =
            "select task_id, name, type, status from task where task_id = :id";
    private static final String SQL_SELECT_TASKS_BY_PROJECT_ID =
            "select task_id, name, type, status from task where project_id = :project_id";
    private static final String SQL_INSERT_TASK =
            "insert into task(name, type, project_id, status) values (:name, :type, :project_id, :status)";
    private static final String SQL_UPDATE_TASK_STATUS =
            "update task set status = :status where task_id = :id";
    private static final String SQL_UPDATE_TASK_TYPE =
            "update task set type = :type where task_id = :id";
    private static final String SQL_DELETE_BY_ID =
            "delete from task where task_id = :id";


    private final NamedParameterJdbcTemplate npjTemplate;

    public TaskRepository(NamedParameterJdbcTemplate npjTemplate) {
        this.npjTemplate = npjTemplate;
    }

    @Nullable
    public Task getTaskById(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
            return npjTemplate.queryForObject(SQL_SELECT_TASK_BY_ID, namedParameters, getTaskRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getTaskById({}) with exception.", id, ex);
        }
        return null;
    }

    @Nullable
    public Collection<Task> getTasksByProjectId(long projectId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("project_id", projectId);
            return npjTemplate.query(SQL_SELECT_TASKS_BY_PROJECT_ID, namedParameters, getTaskRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getTasksByProjectId({}) with exception.", projectId, ex);
        }
        return null;
    }

    public void createTask(TaskDto taskDto) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("name", taskDto.name());
            namedParameters.addValue("type", taskDto.type().getTypeName());
            namedParameters.addValue("status", taskDto.status().getTaskStatusName());
            namedParameters.addValue("project_id", taskDto.projectId());
            npjTemplate.update(SQL_INSERT_TASK, namedParameters);
        } catch (DataAccessException ex) {
            logger.error("Invoke createTask({}) with exception.", taskDto, ex);
        }
    }

    public void updateTaskType(UpdateTaskTypeDto uttDto) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("id", uttDto.taskId());
            namedParameters.addValue("type", uttDto.type().getTypeName());
            npjTemplate.update(SQL_UPDATE_TASK_TYPE, namedParameters);
        } catch (DataAccessException ex) {
            logger.error("Invoke updateTaskType({}) with exception.", uttDto, ex);
        }
    }

    public void updateTaskStatus(UpdateTaskStatusDto utsDto) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("id", utsDto.taskId());
            namedParameters.addValue("status", utsDto.status().getTaskStatusName());
            npjTemplate.update(SQL_UPDATE_TASK_STATUS, namedParameters);
        } catch (DataAccessException ex) {
            logger.error("Invoke updateTaskStatus({}) with exception.", utsDto, ex);
        }
    }

    public void removeTask(long id) {
        try {
            npjTemplate.update(SQL_DELETE_BY_ID, new MapSqlParameterSource("id", id));
        } catch (DataAccessException ex) {
            logger.error("Invoke removeTask({}) with exception.", id, ex);
        }
    }

    private static RowMapper<Task> getTaskRowMapper() {
        return ((rs, rowNum) -> new Task(
                rs.getLong("task_id"),
                rs.getString("name"),
                Task.TaskType.getTaskType(rs.getString("type")),
                Task.TaskStatus.getTaskStatus(rs.getString("status"))
        ));
    }
}
