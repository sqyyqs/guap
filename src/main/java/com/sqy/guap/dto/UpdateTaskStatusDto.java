package com.sqy.guap.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.guap.domain.Task;

public record UpdateTaskStatusDto(
        Task.TaskStatus status,
        Long taskId
) {
    @JsonCreator
    public UpdateTaskStatusDto(@JsonProperty("status") Task.TaskStatus status,
                               @JsonProperty("id") Long taskId) {
        this.status = status;
        this.taskId = taskId;
    }
}
