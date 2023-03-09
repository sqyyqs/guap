package com.sqy.guap.service;

import com.sqy.guap.domain.Task;
import com.sqy.guap.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(long id) {
        return taskRepository.getTaskById(id);
    }

}
