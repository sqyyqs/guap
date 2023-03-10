package com.sqy.guap.service;

import com.sqy.guap.domain.Teacher;
import com.sqy.guap.dto.TeacherDto;
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

    public boolean updateTeacher(TeacherDto dto) {
        return teacherRepository.updateTeacher(dto);
    }

    public boolean removeTeacher(long id) {
        return teacherRepository.removeTeacher(id);
    }

    public boolean createTeacher(TeacherDto dto) {
        return teacherRepository.createTeacher(dto);
    }
}
