package com.sqy.guap;

import com.sqy.guap.controller.StudentController;
import com.sqy.guap.domain.Student;
import com.sqy.guap.dto.StudentDto;
import com.sqy.guap.service.StudentService;
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

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testGetAllStudents() throws Exception {
        Collection<Student> students = Arrays.asList(
                new Student(2L, "name_one"),
                new Student(3L, "name_two")
        );
        Mockito.when(studentService.getAllStudents()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("name_one")))
                .andExpect(jsonPath("$[1].name", is("name_two")))
                .andExpect(jsonPath("$[0].studentId", is(2)))
                .andExpect(jsonPath("$[1].studentId", is(3)));

        Mockito.when(studentService.getAllStudents()).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/project/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student(2L, "michail");
        Mockito.when(studentService.getStudentById(Mockito.anyLong())).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", aMapWithSize(2)))
                .andExpect(jsonPath("$.name", is("michail")))
                .andExpect(jsonPath("$.studentId", is(2)));


        Mockito.when(studentService.getStudentById(Mockito.anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student/2"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student/adsadads"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetStudentsByProjectId() throws Exception {
        Collection<Student> students = Arrays.asList(
                new Student(2L, "name_one"),
                new Student(3L, "name_two"),
                new Student(4L, "wdwd")
        );
        Mockito.when(studentService.getStudentsByProjectId(1)).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student/get-by-project-id?project_id=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("name_one")))
                .andExpect(jsonPath("$[1].name", is("name_two")))
                .andExpect(jsonPath("$[2].name", is("wdwd")))
                .andExpect(jsonPath("$[0].studentId", is(2)))
                .andExpect(jsonPath("$[1].studentId", is(3)))
                .andExpect(jsonPath("$[2].studentId", is(4)));

        Mockito.when(studentService.getStudentsByProjectId(2)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student/get-by-project-id?project_id=2"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student/get-by-project-id?project_id=asdasd"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateStudent() throws Exception {
        Mockito.when(studentService.createStudent(Mockito.any(StudentDto.class))).thenReturn(true);
        StudentDto studentDto = new StudentDto(1L, "Evgeny");
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(studentDto)))
                .andExpect(status().isOk());

        Mockito.when(studentService.createStudent(Mockito.any(StudentDto.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(studentDto)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testRemoveStudent() throws Exception {
        Mockito.when(studentService.removeStudent(Mockito.anyLong())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/student/remove?id=1"))
                .andExpect(status().isOk());

        Mockito.when(studentService.removeStudent(Mockito.anyLong())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/student/remove?id=2"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/student/remove?id=asdasd"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Mockito.when(studentService.updateStudent(Mockito.any(StudentDto.class))).thenReturn(true);
        StudentDto studentDto = new StudentDto(2L, "Max");
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/student/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(studentDto)))
                .andExpect(status().isOk());

        Mockito.when(studentService.updateStudent(Mockito.any(StudentDto.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/student/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(studentDto)))
                .andExpect(status().isNotFound());

    }
}
