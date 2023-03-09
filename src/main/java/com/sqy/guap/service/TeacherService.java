package com.sqy.guap.service;

import com.sqy.guap.domain.Teacher;
import com.sqy.guap.repository.TeacherRepository;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Nullable
    public Teacher getTeacherById(long id) {
        return teacherRepository.getTeacherById(id);
    }
}
