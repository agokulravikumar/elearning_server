package com.edtech.elearning.api;

import com.edtech.elearning.enums.TestType;
import com.edtech.elearning.model.Student;
import com.edtech.elearning.model.StudentCourse;
import com.edtech.elearning.model.StudentTest;
import com.edtech.elearning.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentsController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService mockStudentService;

    @Test
    void testCreateCourse() throws Exception {
        final Student student = new Student();
        student.setStudentId(1);
        student.setStudentName("Sanjay");
        student.setEmailId("sanjay@gmail.com");
        student.setMobileNo("9784563210");
        student.setAddress("5th Main Road");

        final ResponseEntity<Student> studentResponseEntity = new ResponseEntity<>(student, HttpStatusCode.valueOf(200));

        final Student student1 = new Student();
        student1.setStudentName("Sanjay");
        student1.setEmailId("sanjay@gmail.com");
        student1.setMobileNo("9784563210");
        student1.setAddress("5th Main Road");

        when(mockStudentService.createStudent(student1)).thenReturn(studentResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/students")
                        .content(objectMapper.writeValueAsString(student1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(student));
    }

    @Test
    void testEnrollToCourse() throws Exception {
        final StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(3);
        studentCourse.setCourseId(1);
        studentCourse.setEnrollDate(LocalDateTime.of(2024, 1, 11, 0, 0, 0));
        studentCourse.setStatus(1);

        final ResponseEntity<StudentCourse> studentCourseResponseEntity = new ResponseEntity<>(studentCourse,
                HttpStatusCode.valueOf(200));

        when(mockStudentService.enrollStudentToCourse(1, 3))
                .thenReturn(studentCourseResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/students/enrollments")
                        .param("courseId", "1")
                        .param("studentId", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(studentCourse));
    }

    @Test
    void testSubmitQuizzes() throws Exception {
        final StudentTest studentTest = new StudentTest();
        studentTest.setTestAttemptId(1);
        studentTest.setStudentId(3);
        studentTest.setCourseId(1);
        studentTest.setModuleId(1);
        studentTest.setTestId(1);

        final ResponseEntity<StudentTest> studentTestResponseEntity = new ResponseEntity<>(studentTest,
                HttpStatusCode.valueOf(200));

        final StudentTest studentTest1 = new StudentTest();
        studentTest1.setStudentId(3);
        studentTest1.setCourseId(1);
        studentTest1.setModuleId(1);
        studentTest1.setTestId(1);

        when(mockStudentService.submitTests(studentTest1, TestType.QUIZ)).thenReturn(studentTestResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/students/quizzes")
                        .content(objectMapper.writeValueAsString(studentTest1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(studentTest));
    }

    @Test
    void testSubmitAssignment() throws Exception {
        final StudentTest studentTest = new StudentTest();
        studentTest.setTestAttemptId(1);
        studentTest.setStudentId(3);
        studentTest.setCourseId(1);
        studentTest.setModuleId(1);
        studentTest.setTestId(1);

        final ResponseEntity<StudentTest> studentTestResponseEntity = new ResponseEntity<>(studentTest,
                HttpStatusCode.valueOf(200));

        final StudentTest studentTest1 = new StudentTest();
        studentTest1.setStudentId(3);
        studentTest1.setCourseId(1);
        studentTest1.setModuleId(1);
        studentTest1.setTestId(1);

        when(mockStudentService.submitTests(studentTest1, TestType.ASSIGNMENT))
                .thenReturn(studentTestResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/students/assignments")
                        .content(objectMapper.writeValueAsString(studentTest1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(studentTest));
    }

    @Test
    void testGetStudentProgress() throws Exception {
        final Student student = new Student();
        student.setStudentId(3);
        student.setStudentName("Sanjay");
        student.setEmailId("sanjay@gmail.com");
        student.setMobileNo("9874563210");
        student.setAddress("9th Main Road");

        final ResponseEntity<Student> studentResponseEntity = new ResponseEntity<>(student, HttpStatusCode.valueOf(200));

        when(mockStudentService.getStudentProgressData(3)).thenReturn(studentResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/students/{studentId}/progress", 3)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(student));
    }
}
