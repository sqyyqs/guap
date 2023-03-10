package com.sqy.guap.domain;

public record Project(
        long projectId,
        String theme,
        String name,
        long teacherId
) {
}
