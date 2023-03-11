package com.sqy.guap;

import com.sqy.guap.controller.TeacherController;
import com.sqy.guap.domain.Teacher;
import com.sqy.guap.dto.TeacherDto;
import com.sqy.guap.service.TeacherService;
import com.sqy.guap.utils.MappingUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
public class TeacherControllerTests {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TeacherService teacherService;

    @Test
    public void testGetTeacherById() throws Exception {
        Teacher teacher = new Teacher(4L, "Michael", "ITMO");
        Mockito.when(teacherService.getTeacherById(Mockito.anyLong())).thenReturn(teacher);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/teacher/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.teacherId", is(4)))
                .andExpect(jsonPath("$.name", is("Michael")))
                .andExpect(jsonPath("$.establishment", is("ITMO")));
        Mockito.when(teacherService.getTeacherById(Mockito.anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/teacher/444"))
                .andExpect(status().isNotFound());
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/teacher/afkhasgvdoi"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateTeacher() throws Exception {
        TeacherDto teacherDto = new TeacherDto(1L, "Dmitriy", "ITMO");
        Mockito.when(teacherService.createTeacher(Mockito.any(TeacherDto.class))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/teacher/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(teacherDto)))
                .andExpect(status().isOk());

        Mockito.when(teacherService.createTeacher(Mockito.any(TeacherDto.class))).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/teacher/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(teacherDto)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateTeacher() throws Exception {
        TeacherDto teacherDto = new TeacherDto(4L, "Alexey", "SPBGU");
        Mockito.when(teacherService.updateTeacher(Mockito.any(TeacherDto.class))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/teacher/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(teacherDto)))
                .andExpect(status().isOk());

        Mockito.when(teacherService.updateTeacher(Mockito.any(TeacherDto.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/teacher/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(teacherDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoveTeacher() throws Exception {
        Mockito.when(teacherService.removeTeacher(Mockito.anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/teacher/remove?id=2"))
                .andExpect(status().isOk());

        Mockito.when(teacherService.removeTeacher(Mockito.anyLong())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/teacher/remove?id=2"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/teacher/remove?id=sdvnsdofvjno"))
                .andExpect(status().isBadRequest());
    }

}
