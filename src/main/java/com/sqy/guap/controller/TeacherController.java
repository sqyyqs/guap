package com.sqy.guap.controller;

import com.sqy.guap.domain.Teacher;
import com.sqy.guap.dto.TeacherDto;
import com.sqy.guap.service.TeacherService;
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

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable long id) {
        logger.info("Invoke getTeacherById({}).", id);
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teacher);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTeacher(@RequestBody TeacherDto dto) {
        logger.info("Invoke createTeacher({}).", dto);
        boolean status = teacherService.createTeacher(dto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTeacher(@RequestBody TeacherDto dto) {
        logger.info("Invoke updateTeacher({}).", dto);
        boolean status = teacherService.updateTeacher(dto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeTeacher(@RequestParam long id) {
        logger.info("Invoke removeTeacher({}).", id);
        boolean status = teacherService.removeTeacher(id);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }
}
