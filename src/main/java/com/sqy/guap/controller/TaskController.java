package com.sqy.guap.controller;

import com.sqy.guap.domain.Task;
import com.sqy.guap.dto.TaskDto;
import com.sqy.guap.dto.UpdateTaskStatusDto;
import com.sqy.guap.dto.UpdateTaskTypeDto;
import com.sqy.guap.service.TaskService;
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
@RequestMapping("/api/task")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) {
        logger.info("Invoke getTaskById({}).", id);
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/get-by-project-id")
    public ResponseEntity<Collection<Task>> getTasksByProjectId(@RequestParam("project_id") long projectId) {
        logger.info("Invoke getTasksById({}).", projectId);
        Collection<Task> tasks = taskService.getTasksByProjectId(projectId);
        if (tasks == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto) {
        logger.info("Invoke createTask({}).", taskDto);
        taskService.createTask(taskDto);
        return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
    }

    @PutMapping("/update-status")
    public ResponseEntity<String> updateTaskStatus(@RequestBody UpdateTaskStatusDto utsDto) {
        logger.info("Invoke updateTaskStatus({}).", utsDto);
        taskService.updateTaskStatus(utsDto);
        return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
    }

    @PutMapping("/update-type")
    public ResponseEntity<String> updateTaskType(@RequestBody UpdateTaskTypeDto uttDto) {
        logger.info("Invoke updateTaskType({}).", uttDto);
        taskService.updateTaskType(uttDto);
        return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeTask(@RequestParam long id) {
        logger.info("Invoke removeTask({}).", id);
        taskService.removeTask(id);
        return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
    }
}
