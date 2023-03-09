package com.sqy.guap.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.guap.domain.Task;
import jakarta.annotation.Nullable;

import java.util.Objects;

public record TaskDto(
        @Nullable Long taskId,
        Long projectId,
        String name,
        Task.TaskType type,
        Task.TaskStatus status
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TaskDto(@Nullable @JsonProperty("task_id") Long taskId,
                   @JsonProperty("project_id") Long projectId,
                   @JsonProperty("name") String name,
                   @JsonProperty("type") Task.TaskType type,
                   @JsonProperty("status") Task.TaskStatus status) {
        this.taskId = taskId;
        this.projectId = Objects.requireNonNull(projectId);
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.status = Objects.requireNonNull(status);
    }

}
