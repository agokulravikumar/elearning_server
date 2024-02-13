package com.edtech.elearning.api;

import com.edtech.elearning.annotations.StandardResponse;
import com.edtech.elearning.enums.TestType;
import com.edtech.elearning.model.Student;
import com.edtech.elearning.model.StudentCourse;
import com.edtech.elearning.model.StudentTest;
import com.edtech.elearning.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/students")
@Tag(name = "Students", description = "APIs for Students Management")
@StandardResponse
@RequiredArgsConstructor
public class StudentsController {
    private final StudentService studentService;

    @SecurityRequirements
    @Operation(summary = "Create a new Student", description = "Creates a new Student and returns the student details")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createCourse(@RequestBody @Valid Student student) {
        return studentService.createStudent(student);
    }

    @Operation(summary = "Enroll a Student in a Course", description = "Enroll a Student to a Course")
    @PostMapping(path = "/enrollments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentCourse> enrollToCourse(@RequestParam("courseId") int courseId,
                                                        @RequestParam("studentId") int studentId) {
        return studentService.enrollStudentToCourse(courseId, studentId);
    }

    @Operation(summary = "Submit Quiz Answers", description = "Submit the answers for Quiz Questions")
    @PostMapping(path = "/quizzes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentTest> submitQuizzes(@RequestBody @Valid StudentTest studentTest) {
        return studentService.submitTests(studentTest, TestType.QUIZ);
    }

    @Operation(summary = "Submit an Assignment", description = "Submit the assignment for the Student")
    @PostMapping(path = "/assignments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentTest> submitAssignment(@RequestBody @Valid StudentTest studentTest) {
        return studentService.submitTests(studentTest, TestType.ASSIGNMENT);
    }

    @Operation(summary = "Student Progress", description = "Returns the data of Student's progress in Quiz and assignments")
    @GetMapping(path = "/{studentId}/progress", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentProgress(@PathVariable("studentId") int studentId) {
        return studentService.getStudentProgressData(studentId);
    }
}
