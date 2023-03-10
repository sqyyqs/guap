package com.sqy.guap.controller;

import com.sqy.guap.domain.Student;
import com.sqy.guap.dto.StudentDto;
import com.sqy.guap.service.StudentService;
import com.sqy.guap.utils.MappingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        logger.info("Invoke getStudentById({}).", id);
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/get-by-project-id")
    public ResponseEntity<Collection<Student>> getStudentsByProjectId(@RequestParam("project_id") long projectId) {
        logger.info("Invoke getStudentsByProjectId({}).", projectId);
        Collection<Student> student = studentService.getStudentsByProjectId(projectId);
        if (student == null) {
            return notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createStudent(@RequestBody StudentDto studentDto) {
        logger.info("Invoke createStudent({}).", studentDto);
        boolean status = studentService.createStudent(studentDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDto studentDto) {
        logger.info("Invoke updateStudent({}).", studentDto);
        boolean status = studentService.updateStudent(studentDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeStudent(@RequestParam("student_id") long id) {
        logger.info("Invoke removeStudent({}).", id);
        boolean status = studentService.removeStudent(id);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }
}
