package com.edtech.elearning.service;

import com.edtech.elearning.entity.CourseModulesEntity;
import com.edtech.elearning.entity.CoursesEntity;
import com.edtech.elearning.exception.ValidationException;
import com.edtech.elearning.model.Course;
import com.edtech.elearning.model.CourseModule;
import com.edtech.elearning.model.FilterCriteria;
import com.edtech.elearning.model.Filters;
import com.edtech.elearning.repo.CourseModulesRepo;
import com.edtech.elearning.repo.CoursesRepo;
import com.edtech.elearning.util.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CoursesRepo mockCoursesRepo;
    @Mock
    private CourseModulesRepo mockCourseModulesRepo;
    @Mock
    private ModelConverter mockModelConverter;

    private CourseService courseServiceUnderTest;

    @BeforeEach
    void setUp() {
        courseServiceUnderTest = new CourseService(mockCoursesRepo, mockCourseModulesRepo, mockModelConverter);
    }

    @Test
    void testGetAllAvailableCourses() {
        // Setup
        final Filters filters = new Filters();
        filters.setFilter("status,:,1");

        final Course course = new Course();
        course.setCourseId(1);
        course.setCourseTitle("Java & OOPS");
        course.setCourseDesc("An Intro to Java & Oops");
        course.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        course.setEndDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

        final ResponseEntity<List<Course>> expectedResult = new ResponseEntity<>(List.of(course),
                HttpStatusCode.valueOf(200));

        // Configure CoursesRepo.findAll(...).
        final CoursesEntity coursesEntity = new CoursesEntity();
        coursesEntity.setCourseId(1);
        coursesEntity.setCourseTitle("Java & OOPS");
        coursesEntity.setCourseDesc("An Intro to Java & Oops");
        coursesEntity.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        coursesEntity.setEndDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

        final Page<CoursesEntity> coursesEntities = new PageImpl<>(List.of(coursesEntity));

        when(mockCoursesRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(coursesEntities);

        final Course course1 = new Course();
        course1.setCourseId(1);
        course1.setCourseTitle("Java & OOPS");
        course1.setCourseDesc("An Intro to Java & Oops");
        course1.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        course1.setEndDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

        final CoursesEntity entity = new CoursesEntity();
        entity.setCourseId(1);
        entity.setCourseTitle("Java & OOPS");
        entity.setCourseDesc("An Intro to Java & Oops");
        entity.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        entity.setEndDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

        when(mockModelConverter.map(entity, Course.class)).thenReturn(course1);

        // Run the test
        final ResponseEntity<List<Course>> result = courseServiceUnderTest.getAllAvailableCourses(filters,
                PageRequest.of(0, 1));

        // Verify the results
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getBody()).isEqualTo(expectedResult.getBody());
    }

    @Test
    void testGetAllAvailableCourses_CoursesRepoReturnsNoItems() {
        // Setup
        final Filters filters = new Filters();
        filters.setFilter("status,:,1");

        when(mockCoursesRepo.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final ResponseEntity<List<Course>> result = courseServiceUnderTest.getAllAvailableCourses(filters,
                PageRequest.of(0, 1));

        // Verify the results
        assertThat(result.getBody()).isEqualTo(ResponseEntity.ok(Collections.emptyList()).getBody());
    }

    @Test
    void testCreateCourse() {
        // Setup
        final Course course = new Course();
        course.setCourseTitle("Java & OOPS");
        course.setCourseDesc("Intro to Java and OOPS");
        course.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        course.setEndDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

        final Course courseResponse = new Course();
        courseResponse.setCourseId(1);
        courseResponse.setCourseTitle("Java & OOPS");
        courseResponse.setCourseDesc("Intro to Java and OOPS");
        courseResponse.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        courseResponse.setEndDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "Success");

        final ResponseEntity<Course> expectedResult = new ResponseEntity<>(courseResponse, headers, HttpStatusCode.valueOf(200));
        
        final CoursesEntity coursesEntity = new CoursesEntity();
        coursesEntity.setCourseTitle("Java & OOPS");
        coursesEntity.setCourseDesc("An Intro to Java & OOPS");
        coursesEntity.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        coursesEntity.setEndDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

        when(mockModelConverter.map(course, CoursesEntity.class)).thenReturn(coursesEntity);

        final CoursesEntity savedEntity = new CoursesEntity();
        savedEntity.setCourseId(1);
        savedEntity.setCourseTitle("Java & OOPS");
        savedEntity.setCourseDesc("An Intro to Java & OOPS");
        savedEntity.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        savedEntity.setEndDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

        when(mockCoursesRepo.save(coursesEntity)).thenReturn(savedEntity);

        when(mockModelConverter.map(savedEntity, Course.class)).thenReturn(courseResponse);

        // Run the test
        final ResponseEntity<Course> result = courseServiceUnderTest.createCourse(course);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testCreateModule() {
        // Setup
        final CourseModule courseModule = new CourseModule();
        courseModule.setModuleId(1);
        courseModule.setCourseId(1);
        courseModule.setModuleTitle("Intro to Java");
        courseModule.setModuleDesc("Basic introduction of Java");
        courseModule.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        final CourseModule courseModuleResp = new CourseModule();
        courseModuleResp.setModuleId(1);
        courseModuleResp.setCourseId(1);
        courseModuleResp.setModuleTitle("Intro to Java");
        courseModuleResp.setModuleDesc("Basic introduction of Java");
        courseModuleResp.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Message", "Success");

        final ResponseEntity<CourseModule> expectedResult = new ResponseEntity<>(courseModuleResp, httpHeaders,
                HttpStatusCode.valueOf(200));

        when(mockCoursesRepo.existsById(1)).thenReturn(true);

        // Configure ModelConverter.map(...).
        final CourseModulesEntity courseModulesEntity = new CourseModulesEntity();
        courseModulesEntity.setModuleId(1);
        courseModulesEntity.setCourseId(1);
        courseModulesEntity.setModuleTitle("Intro to Java");
        courseModulesEntity.setModuleDesc("Basic introduction of Java");
        courseModulesEntity.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        when(mockModelConverter.map(courseModule, CourseModulesEntity.class)).thenReturn(courseModulesEntity);

        // Configure CourseModulesRepo.save(...).
        final CourseModulesEntity savedCourseModulesEntity = new CourseModulesEntity();
        savedCourseModulesEntity.setModuleId(1);
        savedCourseModulesEntity.setCourseId(1);
        savedCourseModulesEntity.setModuleTitle("Intro to Java");
        savedCourseModulesEntity.setModuleDesc("Basic introduction of Java");
        savedCourseModulesEntity.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        when(mockCourseModulesRepo.save(courseModulesEntity)).thenReturn(savedCourseModulesEntity);

        when(mockModelConverter.map(courseModulesEntity, CourseModule.class)).thenReturn(courseModuleResp);

        // Run the test
        final ResponseEntity<CourseModule> result = courseServiceUnderTest.createModule(1, courseModule);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testCreateModule_CoursesRepoReturnsFalse() {
        // Setup
        final CourseModule courseModule = new CourseModule();
        courseModule.setModuleId(1);
        courseModule.setCourseId(1);
        courseModule.setModuleTitle("Intro to Java");
        courseModule.setModuleDesc("Basic introduction to Java");
        courseModule.setStartDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        when(mockCoursesRepo.existsById(0)).thenReturn(false);

        // Run the test
        assertThatThrownBy(() -> courseServiceUnderTest.createModule(0, courseModule))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void testCourseExistsById() {
        // Setup
        when(mockCoursesRepo.existsById(0)).thenReturn(false);

        // Run the test
        final boolean result = courseServiceUnderTest.courseExistsById(0);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testCourseExistsById_CoursesRepoReturnsTrue() {
        // Setup
        when(mockCoursesRepo.existsById(1)).thenReturn(true);

        // Run the test
        final boolean result = courseServiceUnderTest.courseExistsById(1);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testModuleExistsById() {
        // Setup
        when(mockCourseModulesRepo.existsById(1)).thenReturn(false);

        // Run the test
        final boolean result = courseServiceUnderTest.moduleExistsById(1);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testModuleExistsById_CourseModulesRepoReturnsTrue() {
        // Setup
        when(mockCourseModulesRepo.existsById(1)).thenReturn(true);

        // Run the test
        final boolean result = courseServiceUnderTest.moduleExistsById(1);

        // Verify the results
        assertThat(result).isTrue();
    }
}
