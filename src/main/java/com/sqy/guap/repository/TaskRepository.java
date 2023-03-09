package com.sqy.guap.repository;

import com.sqy.guap.domain.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);
    private static final String SQL_SELECT_TASK_BY_ID = "select * from task where task_id = :id";

    private final NamedParameterJdbcTemplate npjTemplate;

    public TaskRepository(NamedParameterJdbcTemplate npjTemplate) {
        this.npjTemplate = npjTemplate;
    }

    public Task getTaskById(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

            return npjTemplate.queryForObject(SQL_SELECT_TASK_BY_ID, namedParameters, getTaskRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getTaskById({}) with exception.", id, ex);
        }
        return null;
    }

    private static RowMapper<Task> getTaskRowMapper() {
        return ((rs, rowNum) -> new Task(
                rs.getLong("task_id"),
                rs.getString("name"),
                Task.TaskType.getProductListItemType(rs.getString("type"))
        ));
    }
}
