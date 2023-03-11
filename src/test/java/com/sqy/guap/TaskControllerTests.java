package com.sqy.guap;

import com.sqy.guap.controller.TaskController;
import com.sqy.guap.domain.Task;
import com.sqy.guap.dto.TaskDto;
import com.sqy.guap.dto.UpdateTaskStatusDto;
import com.sqy.guap.dto.UpdateTaskTypeDto;
import com.sqy.guap.service.TaskService;
import com.sqy.guap.utils.MappingUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void testGetTaskById() throws Exception {
        Task task = new Task(2L, "do some", Task.TaskType.PRIMARY, Task.TaskStatus.COMPLETED);
        Mockito.when(taskService.getTaskById(2)).thenReturn(task);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/task/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.taskId", is(2)))
                .andExpect(jsonPath("$.status", is("COMPLETED")))
                .andExpect(jsonPath("$.type", is("PRIMARY")))
                .andExpect(jsonPath("$.name", is("do some")));

        Mockito.when(taskService.getTaskById(2)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/task/2"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/task/casscd"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTasksByProjectId() throws Exception {
        Collection<Task> tasks = List.of(
                new Task(2L, "do some", Task.TaskType.PRIMARY, Task.TaskStatus.IN_PROGRESS),
                new Task(3L, "idk", Task.TaskType.SECONDARY, Task.TaskStatus.UNREAD),
                new Task(123L, "qdqd", Task.TaskType.SECONDARY, Task.TaskStatus.COMPLETED)
        );
        Mockito.when(taskService.getTasksByProjectId(Mockito.anyLong())).thenReturn(tasks);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/task/get-by-project-id?project_id=4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].taskId", is(2)))
                .andExpect(jsonPath("$[1].taskId", is(3)))
                .andExpect(jsonPath("$[2].taskId", is(123)))
                .andExpect(jsonPath("$[0].name", is("do some")))
                .andExpect(jsonPath("$[1].name", is("idk")))
                .andExpect(jsonPath("$[2].name", is("qdqd")))
                .andExpect(jsonPath("$[0].type", is("PRIMARY")))
                .andExpect(jsonPath("$[1].type", is("SECONDARY")))
                .andExpect(jsonPath("$[2].type", is("SECONDARY")))
                .andExpect(jsonPath("$[0].status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$[1].status", is("UNREAD")))
                .andExpect(jsonPath("$[2].status", is("COMPLETED")));
        Mockito.when(taskService.getTasksByProjectId(Mockito.anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/task/get-by-project-id?project_id=4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTask() throws Exception {
        Mockito.when(taskService.createTask(Mockito.any(TaskDto.class))).thenReturn(true);
        TaskDto taskDto = new TaskDto(3L, 2L, "do some", Task.TaskType.PRIMARY, Task.TaskStatus.IN_PROGRESS);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/task/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MappingUtils.convertObjectToJson(taskDto)))
                .andExpect(status().isOk());

        Mockito.when(taskService.createTask(taskDto)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(taskDto)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateTaskStatus() throws Exception {
        Mockito.when(taskService.updateTaskStatus(Mockito.any(UpdateTaskStatusDto.class))).thenReturn(true);
        UpdateTaskStatusDto utsDto = new UpdateTaskStatusDto(Task.TaskStatus.COMPLETED, 2L);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/task/update-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(utsDto)))
                .andExpect(status().isOk());
        Mockito.when(taskService.updateTaskStatus(Mockito.any(UpdateTaskStatusDto.class))).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/task/update-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(utsDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateTaskType() throws Exception {
        Mockito.when(taskService.updateTaskType(Mockito.any(UpdateTaskTypeDto.class))).thenReturn(true);
        UpdateTaskTypeDto utsDto = new UpdateTaskTypeDto(Task.TaskType.PRIMARY, 2L);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/task/update-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(utsDto)))
                .andExpect(status().isOk());
        Mockito.when(taskService.updateTaskType(Mockito.any(UpdateTaskTypeDto.class))).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/task/update-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(utsDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoveTaskType() throws Exception {
        Mockito.when(taskService.removeTask(Mockito.anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/task/remove?id=3"))
                .andExpect(status().isOk());
        Mockito.when(taskService.removeTask(Mockito.anyLong())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/task/remove?id=5"))
                .andExpect(status().isNotFound());
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/task/remove?id=asdasd"))
                .andExpect(status().isBadRequest());
    }
}
