package com.sqy.guap.domain;

import jakarta.annotation.Nullable;

import java.io.File;
import java.util.List;

public record Project(
        long projectId,
        String theme,
        String name,
        @Nullable File currentState,
        long teacherId,
        List<Pupil> students
) {
}
