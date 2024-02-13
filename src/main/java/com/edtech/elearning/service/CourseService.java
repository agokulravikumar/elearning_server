package com.edtech.elearning.service;

import com.edtech.elearning.constants.Constants;
import com.edtech.elearning.constants.MsgConstants;
import com.edtech.elearning.entity.CourseModulesEntity;
import com.edtech.elearning.entity.CoursesEntity;
import com.edtech.elearning.enums.SpecialSymbol;
import com.edtech.elearning.exception.ValidationException;
import com.edtech.elearning.model.Course;
import com.edtech.elearning.model.CourseModule;
import com.edtech.elearning.model.FilterCriteria;
import com.edtech.elearning.model.Filters;
import com.edtech.elearning.repo.CourseModulesRepo;
import com.edtech.elearning.repo.CoursesRepo;
import com.edtech.elearning.util.ModelConverter;
import com.edtech.elearning.util.Response;
import com.edtech.elearning.util.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CoursesRepo coursesRepo;
    private final CourseModulesRepo courseModulesRepo;

    private final ModelConverter modelConverter;

    public ResponseEntity<List<Course>> getAllAvailableCourses(Filters filters, Pageable pageable) {
        var filterCriteria = new FilterCriteria();
        filterCriteria.setKey(Constants.STATUS);
        filterCriteria.setOperation(SpecialSymbol.EQUALS.getPlain());
        filterCriteria.setValue(String.valueOf(Constants.ACTIVE));

        filters.getFilterCriteria().add(filterCriteria);

        Specification<CoursesEntity> specification = new SpecificationBuilder<CoursesEntity>(filters).getSpecification();

        Page<CoursesEntity> courseEntities = coursesRepo.findAll(specification, pageable);

        List<Course> courses = courseEntities.stream()
                .map(courseEntity -> modelConverter.map(courseEntity, Course.class))
                .toList();

        return Response.success(courseEntities, courses);
    }

    public ResponseEntity<Course> createCourse(Course course) {
        CoursesEntity coursesEntity = modelConverter.map(course, CoursesEntity.class);

        CoursesEntity savedCourse = coursesRepo.save(coursesEntity);

        course = modelConverter.map(savedCourse, Course.class);

        return Response.success(course);
    }

    @SneakyThrows
    public ResponseEntity<CourseModule> createModule(int courseId, CourseModule courseModule) {
        if (!coursesRepo.existsById(courseId)) {
            throw new ValidationException(MsgConstants.INVALID_COURSE_ID);
        }

        CourseModulesEntity courseModulesEntity = modelConverter.map(courseModule, CourseModulesEntity.class);
        courseModulesEntity.setCourseId(courseId);

        CourseModulesEntity savedModule = courseModulesRepo.save(courseModulesEntity);

        courseModule = modelConverter.map(savedModule, CourseModule.class);

        return Response.success(courseModule);
    }

    public boolean courseExistsById(int courseId) {
        return coursesRepo.existsById(courseId);
    }

    public boolean moduleExistsById(int moduleId) {
        return courseModulesRepo.existsById(moduleId);
    }
}
