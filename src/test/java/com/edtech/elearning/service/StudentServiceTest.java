package com.edtech.elearning.service;

import com.edtech.elearning.entity.StudentCoursesEntity;
import com.edtech.elearning.entity.StudentCoursesEntityPk;
import com.edtech.elearning.entity.StudentTestsEntity;
import com.edtech.elearning.entity.StudentsEntity;
import com.edtech.elearning.enums.TestType;
import com.edtech.elearning.exception.ValidationException;
import com.edtech.elearning.model.Student;
import com.edtech.elearning.model.StudentCourse;
import com.edtech.elearning.model.StudentTest;
import com.edtech.elearning.repo.StudentCoursesRepo;
import com.edtech.elearning.repo.StudentTestsRepo;
import com.edtech.elearning.repo.StudentsRepo;
import com.edtech.elearning.util.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private CourseService mockCourseService;
    @Mock
    private StudentsRepo mockStudentsRepo;
    @Mock
    private StudentCoursesRepo mockStudentCoursesRepo;
    @Mock
    private StudentTestsRepo mockStudentTestsRepo;
    @Mock
    private ModelConverter mockModelConverter;

    private StudentService studentServiceUnderTest;

    @BeforeEach
    void setUp() {
        studentServiceUnderTest = new StudentService(mockCourseService, mockStudentsRepo, mockStudentCoursesRepo,
                mockStudentTestsRepo, mockModelConverter);
    }

    @Test
    void testCreateStudent() {
        // Setup
        final Student student = new Student();
        student.setStudentName("Sanjay");
        student.setEmailId("test@gmail.com");
        student.setMobileNo("9874563210");
        student.setAddress("5th Main Road");

        final Student studentResp = new Student();
        student.setStudentId(1);
        student.setStudentName("Sanjay");
        studentResp.setEmailId("test@gmail.com");
        studentResp.setMobileNo("9874563210");
        student.setAddress("5th Main Road");

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Message", "Success");

        final ResponseEntity<Student> expectedResult = new ResponseEntity<>(studentResp, httpHeaders,
                HttpStatusCode.valueOf(200));

        // Configure ModelConverter.map(...).
        final StudentsEntity studentsEntity = new StudentsEntity();
        studentsEntity.setStudentName("Sanjay");
        studentsEntity.setEmailId("test@gmail.com");
        studentsEntity.setMobileNo("9874563210");
        studentsEntity.setAddress("5th Main Road");

        when(mockModelConverter.map(student, StudentsEntity.class)).thenReturn(studentsEntity);

        // Configure StudentsRepo.save(...).
        final StudentsEntity savedStudentsEntity = new StudentsEntity();
        savedStudentsEntity.setStudentId(1);
        savedStudentsEntity.setStudentName("Sanjay");
        savedStudentsEntity.setEmailId("test@gmail.com");
        savedStudentsEntity.setMobileNo("9874563210");
        savedStudentsEntity.setAddress("5th Main Road");

        when(mockStudentsRepo.save(studentsEntity)).thenReturn(savedStudentsEntity);

        when(mockModelConverter.map(savedStudentsEntity, Student.class)).thenReturn(studentResp);

        // Run the test
        final ResponseEntity<Student> result = studentServiceUnderTest.createStudent(student);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEnrollStudentToCourse() {
        // Setup
        final StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(1);
        studentCourse.setCourseId(1);
        studentCourse.setEnrollDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        studentCourse.setStatus(1);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Message", "Success");

        final ResponseEntity<StudentCourse> expectedResult = new ResponseEntity<>(studentCourse, httpHeaders,
                HttpStatusCode.valueOf(200));

        when(mockCourseService.courseExistsById(1)).thenReturn(true);

        when(mockStudentsRepo.existsById(1)).thenReturn(true);

        // Configure StudentCoursesRepo.save(...).
        final StudentCoursesEntityPk pk = new StudentCoursesEntityPk();
        pk.setStudentId(1);
        pk.setCourseId(1);

        LocalDateTime enrollDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        final StudentCoursesEntity studentCoursesEntity = new StudentCoursesEntity();
        studentCoursesEntity.setPk(pk);
        studentCoursesEntity.setEnrollDate(enrollDate);
        studentCoursesEntity.setStatus(1);

        final StudentCoursesEntityPk savedPk = new StudentCoursesEntityPk();
        savedPk.setStudentId(1);
        savedPk.setCourseId(1);

        final StudentCoursesEntity savedStudentCoursesEntity = new StudentCoursesEntity();
        savedStudentCoursesEntity.setPk(savedPk);
        savedStudentCoursesEntity.setEnrollDate(enrollDate);
        savedStudentCoursesEntity.setStatus(1);

        when(mockStudentCoursesRepo.save(studentCoursesEntity)).thenReturn(savedStudentCoursesEntity);

        when(mockModelConverter.map(studentCoursesEntity, StudentCourse.class)).thenReturn(studentCourse);

        // Run the test
        final ResponseEntity<StudentCourse> result = studentServiceUnderTest.enrollStudentToCourse(1, 1);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testEnrollStudentToCourse_CourseServiceReturnsFalse() {
        // Setup
        when(mockCourseService.courseExistsById(0)).thenReturn(false);

        // Run the test
        assertThatThrownBy(() -> studentServiceUnderTest.enrollStudentToCourse(0, 1))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void testEnrollStudentToCourse_StudentsRepoReturnsFalse() {
        // Setup
        when(mockCourseService.courseExistsById(1)).thenReturn(true);

        when(mockStudentsRepo.existsById(0)).thenReturn(false);

        // Run the test
        assertThatThrownBy(() -> studentServiceUnderTest.enrollStudentToCourse(1, 0))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void testSubmitTests() {
        // Setup
        final StudentTest studentTest = new StudentTest();
        studentTest.setTestAttemptId(1);
        studentTest.setStudentId(1);
        studentTest.setCourseId(1);
        studentTest.setModuleId(1);
        studentTest.setTestId(1);

        final StudentTest studentTestResp = new StudentTest();
        studentTestResp.setTestAttemptId(0);
        studentTestResp.setStudentId(0);
        studentTestResp.setCourseId(0);
        studentTestResp.setModuleId(0);
        studentTestResp.setTestId(0);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Message", "Success");

        final ResponseEntity<StudentTest> expectedResult = new ResponseEntity<>(studentTestResp, httpHeaders,
                HttpStatusCode.valueOf(200));

        when(mockCourseService.courseExistsById(1)).thenReturn(true);
        when(mockStudentsRepo.existsById(1)).thenReturn(true);
        when(mockCourseService.moduleExistsById(1)).thenReturn(true);

        // Configure ModelConverter.map(...).
        final StudentTestsEntity studentTestsEntity = new StudentTestsEntity();
        studentTestsEntity.setTestAttemptId(1);
        studentTestsEntity.setStudentId(1);
        studentTestsEntity.setCourseId(1);
        studentTestsEntity.setModuleId(1);
        studentTestsEntity.setTestType(1);
        studentTestsEntity.setTestScore(67.00);

        when(mockModelConverter.map(studentTest, StudentTestsEntity.class)).thenReturn(studentTestsEntity);

        when(mockStudentTestsRepo.save(studentTestsEntity)).thenReturn(studentTestsEntity);

        when(mockModelConverter.map(studentTestsEntity, StudentTest.class)).thenReturn(studentTestResp);

        // Run the test
        final ResponseEntity<StudentTest> result = studentServiceUnderTest.submitTests(studentTest, TestType.QUIZ);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSubmitTests_CourseServiceCourseExistsByIdReturnsFalse() {
        // Setup
        final StudentTest studentTest = new StudentTest();
        studentTest.setTestAttemptId(1);
        studentTest.setStudentId(1);
        studentTest.setCourseId(0);
        studentTest.setModuleId(1);
        studentTest.setTestId(1);

        when(mockCourseService.courseExistsById(0)).thenReturn(false);

        // Run the test
        assertThatThrownBy(() -> studentServiceUnderTest.submitTests(studentTest, TestType.QUIZ))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void testSubmitTests_StudentsRepoReturnsFalse() {
        // Setup
        final StudentTest studentTest = new StudentTest();
        studentTest.setTestAttemptId(1);
        studentTest.setStudentId(0);
        studentTest.setCourseId(1);
        studentTest.setModuleId(1);
        studentTest.setTestId(1);

        when(mockCourseService.courseExistsById(1)).thenReturn(true);

        when(mockStudentsRepo.existsById(0)).thenReturn(false);

        // Run the test
        assertThatThrownBy(() -> studentServiceUnderTest.submitTests(studentTest, TestType.QUIZ))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void testSubmitTests_CourseServiceModuleExistsByIdReturnsFalse() {
        // Setup
        final StudentTest studentTest = new StudentTest();
        studentTest.setTestAttemptId(1);
        studentTest.setStudentId(1);
        studentTest.setCourseId(1);
        studentTest.setModuleId(5);
        studentTest.setTestId(1);

        final StudentTestsEntity studentTestsEntity = new StudentTestsEntity();
        studentTestsEntity.setTestAttemptId(1);
        studentTestsEntity.setStudentId(1);
        studentTestsEntity.setCourseId(1);
        studentTestsEntity.setModuleId(5);
        studentTestsEntity.setTestId(1);

        when(mockCourseService.courseExistsById(1)).thenReturn(true);
        when(mockStudentsRepo.existsById(1)).thenReturn(true);
        when(mockCourseService.moduleExistsById(5)).thenReturn(false);

        // Run the test
        assertThatThrownBy(() -> studentServiceUnderTest.submitTests(studentTest, TestType.QUIZ))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void testGetStudentProgressData() {
        // Setup
        final StudentTest studentTest = new StudentTest();
        studentTest.setTestAttemptId(1);
        studentTest.setStudentId(1);
        studentTest.setCourseId(1);
        studentTest.setModuleId(1);
        studentTest.setTestId(1);
        studentTest.setTestType(1);
        studentTest.setTestData("Test Data");
        studentTest.setTestScore(75.00);

        final Student student = new Student();
        student.setStudentId(1);
        student.setStudentName("Sanjay");
        student.setEmailId("test@gmail.com");
        student.setMobileNo("9874563210");
        student.setStudentTests(List.of(studentTest));

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Message", "Success");

        final ResponseEntity<Student> expectedResult = new ResponseEntity<>(student, httpHeaders,
                HttpStatusCode.valueOf(200));

        // Configure StudentsRepo.findById(...).
        final StudentsEntity studentsEntity1 = new StudentsEntity();
        studentsEntity1.setStudentId(1);
        studentsEntity1.setStudentName("Sanjay");
        studentsEntity1.setEmailId("test@gmail.com");
        studentsEntity1.setMobileNo("9874563210");

        final Optional<StudentsEntity> studentsEntity = Optional.of(studentsEntity1);

        when(mockStudentsRepo.findById(1)).thenReturn(studentsEntity);

        when(mockModelConverter.map(studentsEntity.get(), Student.class)).thenReturn(student);

        // Configure StudentTestsRepo.findAllByStudentId(...).
        final StudentTestsEntity studentTestsEntity = new StudentTestsEntity();
        studentTestsEntity.setTestAttemptId(1);
        studentTestsEntity.setStudentId(1);
        studentTestsEntity.setCourseId(1);
        studentTestsEntity.setModuleId(1);
        studentTestsEntity.setTestId(1);
        studentTestsEntity.setTestType(1);
        studentTestsEntity.setTestData("Test Data");
        studentTestsEntity.setTestScore(75.00);

        final List<StudentTestsEntity> studentTestsEntities = List.of(studentTestsEntity);

        when(mockStudentTestsRepo.findAllByStudentId(1)).thenReturn(studentTestsEntities);

        // Run the test
        final ResponseEntity<Student> result = studentServiceUnderTest.getStudentProgressData(1);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetStudentProgressData_StudentsRepoReturnsAbsent() {
        // Setup
        when(mockStudentsRepo.findById(0)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> studentServiceUnderTest.getStudentProgressData(0))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void testGetStudentProgressData_StudentTestsRepoReturnsNoItems() {
        // Setup
        final Student student = new Student();
        student.setStudentId(1);
        student.setStudentName("Sanjay");
        student.setEmailId("test@gmail.com");
        student.setMobileNo("9874563210");
        student.setAddress("5th Main Road");

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Message", "Success");

        final ResponseEntity<Student> expectedResult = new ResponseEntity<>(student, httpHeaders,
                HttpStatusCode.valueOf(200));

        // Configure StudentsRepo.findById(...).
        final StudentsEntity studentsEntity1 = new StudentsEntity();
        studentsEntity1.setStudentId(1);
        studentsEntity1.setStudentName("Sanjay");
        studentsEntity1.setEmailId("test@gmail.com");
        studentsEntity1.setMobileNo("9874563210");
        studentsEntity1.setAddress("5th Main Road");

        final Optional<StudentsEntity> studentsEntity = Optional.of(studentsEntity1);

        when(mockStudentsRepo.findById(1)).thenReturn(studentsEntity);

        when(mockModelConverter.map(studentsEntity.get(), Student.class)).thenReturn(student);

        when(mockStudentTestsRepo.findAllByStudentId(1)).thenReturn(Collections.emptyList());

        // Run the test
        final ResponseEntity<Student> result = studentServiceUnderTest.getStudentProgressData(1);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
