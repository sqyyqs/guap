package com.sqy.guap.controller;


import com.sqy.guap.domain.Project;
import com.sqy.guap.dto.ProjectDto;
import com.sqy.guap.service.ProjectService;
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

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable long id) {
        logger.info("Invoke getProjectById({}).", id);
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(project);
    }

    @GetMapping("/get-by-student-id")
    public ResponseEntity<Collection<Project>> getProjectsByStudentId(@RequestParam("student_id") long id) {
        logger.info("Invoke getProjectsByStudentId({}).", id);
        Collection<Project> project = projectService.getProjectsByStudentId(id);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(project);
    }

    @GetMapping("/get-by-teacher-id")
    public ResponseEntity<Collection<Project>> getProjectsByTeacherId(@RequestParam("teacher_id") long id) {
        logger.info("Invoke getProjectsByTeacherId({}).", id);
        Collection<Project> project = projectService.getProjectsByTeacherId(id);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(project);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProject(@RequestBody ProjectDto projectDto) {
        logger.info("Invoke updateProject({}).", projectDto);
        projectService.updateProject(projectDto);
        return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestBody ProjectDto projectDto) {
        logger.info("Invoke createProject({}).", projectDto);
        projectService.createProject(projectDto);
        return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProject(@RequestParam("project_id") long id) {
        logger.info("Invoke removeProject({}).", id);
        projectService.removeProject(id);
        return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
    }

}
