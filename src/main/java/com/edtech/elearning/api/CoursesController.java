package com.edtech.elearning.api;

import com.edtech.elearning.annotations.ApiFilterable;
import com.edtech.elearning.annotations.ApiPageable;
import com.edtech.elearning.annotations.StandardResponse;
import com.edtech.elearning.model.Course;
import com.edtech.elearning.model.CourseModule;
import com.edtech.elearning.model.Filters;
import com.edtech.elearning.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/courses")
@Tag(name = "Courses", description = "APIs for Course Management")
@StandardResponse
@RequiredArgsConstructor
@SecurityRequirements
public class CoursesController {
    private final CourseService courseService;

    @ApiPageable
    @ApiFilterable
    @Operation(summary = "Access all Available Courses", description = "Returns a list of all available Courses")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAvailableCourses(@Parameter(hidden = true) Filters filters,
                                                            @Parameter(hidden = true) Pageable pageable) {
        return courseService.getAllAvailableCourses(filters, pageable);
    }

    @Operation(summary = "Create a new Course", description = "Creates a new Course and returns the course details")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> createCourse(@RequestBody @Valid Course course) {
        return courseService.createCourse(course);
    }

    @Operation(summary = "Add a module to Course", description = "Creates a new Module for an existing Course")
    @PostMapping(path = "/{courseId}/modules", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseModule> createModule(@PathVariable("courseId") int courseId,
                                                     @RequestBody @Valid CourseModule courseModule) {
        return courseService.createModule(courseId, courseModule);
    }

}
