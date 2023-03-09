package com.sqy.guap.service;

import com.sqy.guap.domain.Student;
import com.sqy.guap.repository.StudentRepository;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Nullable
    public Student getStudentById(long id) {
        return studentRepository.getStudentById(id);
    }

    @Nullable
    public Collection<Student> getStudentsByProjectId(long projectId) {
        return studentRepository.getStudentsByProjectId(projectId);
    }
}
