package com.sqy.guap.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record TeacherDto(
        @Nullable Long teacher_id,
        String name,
        String establishment
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TeacherDto(@Nullable @JsonProperty("id") Long teacher_id,
                      @JsonProperty("name") String name,
                      @JsonProperty("establishment") String establishment) {
        this.teacher_id = teacher_id;
        this.name = name;
        this.establishment = establishment;
    }
}
