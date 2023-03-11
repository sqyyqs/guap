package com.sqy.guap;

import com.sqy.guap.controller.ProjectController;
import com.sqy.guap.domain.Project;
import com.sqy.guap.dto.ProjectDto;
import com.sqy.guap.service.ProjectService;
import com.sqy.guap.utils.MappingUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProjectController.class)
class ProjectControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    public void testGetProjectById() throws Exception {
        Project project = new Project(1L, "theme", "name", 2L);

        Mockito.when(projectService.getProjectById(Mockito.anyLong())).thenReturn(project);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/project/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.theme", is("theme")))
                .andExpect(jsonPath("$.projectId", is(1)))
                .andExpect(jsonPath("$.teacherId", is(2)));

        Mockito.when(projectService.getProjectById(Mockito.anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/project/2"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/project/adsadads"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllProjects() throws Exception {
        List<Project> projects = Arrays.asList(
                new Project(2L, "SUAI application", "java17", 1L),
                new Project(3L, "Random Service", "python", 2L)
        );

        Mockito.when(projectService.getAllProjects()).thenReturn(projects);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/project/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("java17")))
                .andExpect(jsonPath("$[1].name", is("python")))
                .andExpect(jsonPath("$[0].theme", is("SUAI application")))
                .andExpect(jsonPath("$[1].theme", is("Random Service")))
                .andExpect(jsonPath("$[0].projectId", is(2)))
                .andExpect(jsonPath("$[1].projectId", is(3)))
                .andExpect(jsonPath("$[0].teacherId", is(1)))
                .andExpect(jsonPath("$[1].teacherId", is(2)));

        Mockito.when(projectService.getAllProjects()).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/project/all"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/project/all?id=12&sd=qdq"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProjectsByStudentId() throws Exception {
        Collection<Project> projects = List.of(
                new Project(1L, "theme", "name", 2L),
                new Project(2L, "theme2", "name2", 2L)
        );
        Mockito.when(projectService.getProjectsByStudentId(2)).thenReturn(projects);
        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:8080/api/project/get-by-student-id?student_id=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].theme", is("theme")))
                .andExpect(jsonPath("$[0].projectId", is(1)))
                .andExpect(jsonPath("$[0].teacherId", is(2)))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[1].theme", is("theme2")))
                .andExpect(jsonPath("$[1].projectId", is(2)))
                .andExpect(jsonPath("$[1].teacherId", is(2)));

        Mockito.when(projectService.getProjectsByStudentId(123123)).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:8080/api/project/get-by-student-id?student_id=123123"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:8080/api/project/get-by-student-id?student_id=dcsdvdfv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetProjectByTeacherId() throws Exception {
        Collection<Project> projects = List.of(
                new Project(1L, "theme", "name", 3L),
                new Project(2L, "theme2", "name2", 3L)
        );

        Mockito.when(projectService.getProjectsByTeacherId(Mockito.anyLong())).thenReturn(projects);
        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:8080/api/project/get-by-teacher-id?teacher_id=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].theme", is("theme")))
                .andExpect(jsonPath("$[0].projectId", is(1)))
                .andExpect(jsonPath("$[0].teacherId", is(3)))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[1].theme", is("theme2")))
                .andExpect(jsonPath("$[1].projectId", is(2)))
                .andExpect(jsonPath("$[1].teacherId", is(3)));

        Mockito.when(projectService.getProjectsByTeacherId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:8080/api/project/get-by-teacher-id?teacher_id=123123"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get(
                        "http://localhost:8080/api/project/get-by-teacher-id?teacher_id=sdacz"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateProject() throws Exception {
        Mockito.when(projectService.updateProject(Mockito.any(ProjectDto.class))).thenReturn(true);
        ProjectDto projectDto = new ProjectDto(1L, "themeee", "nameee", 1L);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/project/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(projectDto)))
                .andExpect(status().isOk());

        Mockito.when(projectService.updateProject(Mockito.any(ProjectDto.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/project/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(projectDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoveProject() throws Exception {
        Mockito.when(projectService.removeProject(Mockito.anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/project/remove?id=1"))
                .andExpect(status().isOk());

        Mockito.when(projectService.removeProject(Mockito.anyLong())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/project/remove?id=1"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/project/remove?id=asdasd"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateProject() throws Exception {
        Mockito.when(projectService.createProject(Mockito.any(ProjectDto.class))).thenReturn(true);
        ProjectDto projectDto = new ProjectDto(2L, "themeee2", "nameee2", 2L);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(projectDto)))
                .andExpect(status().isOk());

        Mockito.when(projectService.createProject(Mockito.any(ProjectDto.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(projectDto)))
                .andExpect(status().isConflict());
    }
}

