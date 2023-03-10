package com.sqy.guap.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record StudentDto(
        @Nullable Long id,
        String name
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public StudentDto(
            @Nullable @JsonProperty("id") Long id,
            @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}
