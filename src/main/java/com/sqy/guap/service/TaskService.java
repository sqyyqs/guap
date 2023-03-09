package com.sqy.guap.service;

import com.sqy.guap.domain.Task;
import com.sqy.guap.dto.TaskDto;
import com.sqy.guap.dto.UpdateTaskStatusDto;
import com.sqy.guap.dto.UpdateTaskTypeDto;
import com.sqy.guap.repository.TaskRepository;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Nullable
    public Task getTaskById(long id) {
        return taskRepository.getTaskById(id);
    }

    @Nullable
    public Collection<Task> getTasksByProjectId(long projectId) {
        return taskRepository.getTasksByProjectId(projectId);
    }

    public void createTask(TaskDto taskDto) {
        taskRepository.createTask(taskDto);
    }

    public void updateTaskType(UpdateTaskTypeDto uttDto) {
        taskRepository.updateTaskType(uttDto);
    }

    public void updateTaskStatus(UpdateTaskStatusDto utsDto) {
        taskRepository.updateTaskStatus(utsDto);
    }

    public void removeTask(long id) {
        taskRepository.removeTask(id);
    }
}
