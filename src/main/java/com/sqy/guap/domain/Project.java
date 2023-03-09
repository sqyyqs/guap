package com.sqy.guap.domain;

import jakarta.annotation.Nullable;

import java.io.File;

public record Project(
        long projectId,
        String theme,
        String name,
        @Nullable File currentState,
        long teacherId
) {
}
