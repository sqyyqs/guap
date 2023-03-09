package com.sqy.guap.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.guap.domain.Task;

public record UpdateTaskTypeDto(
        Task.TaskType type,
        Long taskId
) {
    @JsonCreator
    public UpdateTaskTypeDto(@JsonProperty("type") Task.TaskType type,
                             @JsonProperty("id") Long taskId) {
        this.type = type;
        this.taskId = taskId;
    }
}
