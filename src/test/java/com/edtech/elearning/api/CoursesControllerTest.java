package com.edtech.elearning.api;

import com.edtech.elearning.model.Course;
import com.edtech.elearning.model.CourseModule;
import com.edtech.elearning.model.Filters;
import com.edtech.elearning.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CoursesController.class)
@AutoConfigureMockMvc(addFilters = false)
class CoursesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService mockCourseService;

    @Test
    void testGetAvailableCourses() throws Exception {
        final Course course = new Course();
        course.setCourseId(1);
        course.setCourseTitle("Advanced Java");
        course.setCourseDesc("Detail approach to Advanced Java");
        course.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        course.setEndDate(LocalDateTime.of(2026, 1, 1, 0, 0, 0));
        course.setStatus(1);

        final ResponseEntity<List<Course>> listResponseEntity = new ResponseEntity<>(List.of(course),
                HttpStatusCode.valueOf(200));

        when(mockCourseService.getAllAvailableCourses(any(Filters.class), any(Pageable.class)))
                .thenReturn(listResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/courses")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(List.of(course)));
    }

    @Test
    void testGetAvailableCourses_CourseServiceReturnsNoItems() throws Exception {
        final ResponseEntity<List<Course>> listResponseEntity = ResponseEntity.ok(Collections.emptyList());

        when(mockCourseService.getAllAvailableCourses(any(Filters.class), any(Pageable.class)))
                .thenReturn(listResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/courses")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testCreateCourse() throws Exception {
        final Course course = new Course();
        course.setCourseId(1);
        course.setCourseTitle("Advanced Java");
        course.setCourseDesc("Detail approach to Advanced Java");
        course.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        course.setEndDate(LocalDateTime.of(2026, 1, 1, 0, 0, 0));

        final ResponseEntity<Course> courseResponseEntity = new ResponseEntity<>(course, HttpStatusCode.valueOf(200));

        final Course course1 = new Course();
        course1.setCourseTitle("Advanced Java");
        course1.setCourseDesc("Detail approach to Advanced Java");
        course1.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        course1.setEndDate(LocalDateTime.of(2026, 1, 1, 0, 0, 0));

        when(mockCourseService.createCourse(course1)).thenReturn(courseResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/courses")
                        .content(objectMapper.writeValueAsString(course1)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(course));
    }

    @Test
    void testCreateModule() throws Exception {
        final CourseModule courseModule = new CourseModule();
        courseModule.setModuleId(1);
        courseModule.setCourseId(1);
        courseModule.setModuleTitle("Java Intro");
        courseModule.setModuleDesc("Basic introduction to Java");
        courseModule.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        final ResponseEntity<CourseModule> courseModuleResponseEntity = new ResponseEntity<>(courseModule,
                HttpStatusCode.valueOf(200));

        final CourseModule courseModule1 = new CourseModule();
        courseModule.setModuleTitle("Java Intro");
        courseModule.setModuleDesc("Basic introduction to Java");
        courseModule.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        when(mockCourseService.createModule(1, courseModule1)).thenReturn(courseModuleResponseEntity);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/courses/{courseId}/modules", 1)
                        .content(objectMapper.writeValueAsString(courseModule1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(courseModule));
    }
}
