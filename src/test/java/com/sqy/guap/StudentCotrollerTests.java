package com.sqy.guap;

import com.sqy.guap.controller.ProjectController;
import com.sqy.guap.controller.StudentController;
import com.sqy.guap.domain.Project;
import com.sqy.guap.domain.Student;
import com.sqy.guap.service.ProjectService;
import com.sqy.guap.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProjectController.class)
class GuapApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    public void testAllProjects() throws Exception {
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
                .andExpect(jsonPath("$[1].name", is("python")));
    }

}

@WebMvcTest(StudentController.class)
class StudentCotrollerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testAllProjects() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "Konstantin Demin"),
                new Student(2L, "Vyacheslav Kirov"),
                new Student(3L, "Dmitriy Medvedev"),
                new Student(4L, "Егорыч))))")
        );

        Mockito.when(studentService.getAllStudents()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name", is(students.get(0).name())))
                .andExpect(jsonPath("$[1].name", is("Vyacheslav Kirov")))
                .andExpect(jsonPath("$[2].name", is("Dmitriy Medvedev")))
                .andExpect(jsonPath("$[3].name", is("Егорыч))))")));
    }
}
