package com.sqy.guap.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record ProjectDto(
        @Nullable Long projectId,
        String theme,
        String name,
        Long teacherId
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ProjectDto(
            @Nullable @JsonProperty("id") Long projectId,
            @JsonProperty("theme") String theme,
            @JsonProperty("name") String name,
            @JsonProperty("teacherId") Long teacherId
    ) {
        this.projectId = projectId;
        this.theme = theme;
        this.name = name;
        this.teacherId = teacherId;
    }
}
