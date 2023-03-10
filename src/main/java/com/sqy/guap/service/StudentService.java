package com.sqy.guap.service;

import com.sqy.guap.domain.Student;
import com.sqy.guap.dto.StudentDto;
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

    public boolean createStudent(StudentDto studentDto) {
        return studentRepository.createStudent(studentDto);
    }

    public boolean updateStudent(StudentDto studentDto) {
        return studentRepository.updateStudent(studentDto);
    }

    public boolean removeStudent(long id) {
        return studentRepository.removeStudent(id);
    }
}
