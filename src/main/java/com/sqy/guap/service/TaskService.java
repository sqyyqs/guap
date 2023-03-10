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

    public boolean createTask(TaskDto taskDto) {
        return taskRepository.createTask(taskDto);
    }

    public boolean updateTaskType(UpdateTaskTypeDto uttDto) {
        return taskRepository.updateTaskType(uttDto);
    }

    public boolean updateTaskStatus(UpdateTaskStatusDto utsDto) {
        return taskRepository.updateTaskStatus(utsDto);
    }

    public boolean removeTask(long id) {
        return taskRepository.removeTask(id);
    }
}
