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
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Project>> getAllProjects() {
        logger.info("Invoke getAllProjects().");
        Collection<Project> projects = projectService.getAllProjects();
        if (projects == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/get-by-student-id")
    public ResponseEntity<Collection<Project>> getProjectsByStudentId(@RequestParam("student_id") long id) {
        logger.info("Invoke getProjectsByStudentId({}).", id);
        Collection<Project> projects = projectService.getProjectsByStudentId(id);
        if (projects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/get-by-teacher-id")
    public ResponseEntity<Collection<Project>> getProjectsByTeacherId(@RequestParam("teacher_id") long id) {
        logger.info("Invoke getProjectsByTeacherId({}).", id);
        Collection<Project> project = projectService.getProjectsByTeacherId(id);
        if (project.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProject(@RequestBody ProjectDto projectDto) {
        logger.info("Invoke updateProject({}).", projectDto);
        boolean status = projectService.updateProject(projectDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestBody ProjectDto projectDto) {
        logger.info("Invoke createProject({}).", projectDto);
        boolean status = projectService.createProject(projectDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProject(@RequestParam("id") long id) {
        logger.info("Invoke removeProject({}).", id);
        boolean status = projectService.removeProject(id);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }

}
