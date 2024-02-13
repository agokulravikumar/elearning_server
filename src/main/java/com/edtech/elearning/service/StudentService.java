package com.edtech.elearning.service;

import com.edtech.elearning.constants.Constants;
import com.edtech.elearning.constants.MsgConstants;
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
import com.edtech.elearning.util.Response;
import com.edtech.elearning.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final CourseService courseService;

    private final StudentsRepo studentsRepo;
    private final StudentCoursesRepo studentCoursesRepo;
    private final StudentTestsRepo studentTestsRepo;

    private final ModelConverter modelConverter;

    private final Random random = new Random();

    @SneakyThrows
    public ResponseEntity<Student> createStudent(Student student) {
        validateStudent(student);

        StudentsEntity studentsEntity = modelConverter.map(student, StudentsEntity.class);

        StudentsEntity savedStudent = studentsRepo.save(studentsEntity);

        student = mapEntityToModel(savedStudent);

        return Response.success(student);
    }

    private void validateStudent(Student student) throws ValidationException {
        if (!ValidationUtil.isValidEmailId(student.getEmailId())) {
            throw new ValidationException(MsgConstants.EMAIL_ID_INVALID);
        } else if (!ValidationUtil.isValidMobileNo(student.getMobileNo())) {
            throw new ValidationException(MsgConstants.MOBILE_NO_INVALID);
        }
    }

    @SneakyThrows
    public ResponseEntity<StudentCourse> enrollStudentToCourse(int courseId, int studentId) {
        if (!courseService.courseExistsById(courseId)) {
            throw new ValidationException(MsgConstants.INVALID_COURSE_ID);
        } else if (!studentsRepo.existsById(studentId)) {
            throw new ValidationException(MsgConstants.STUDENT_NOT_AVAILABLE);
        }

        StudentCoursesEntityPk pk = new StudentCoursesEntityPk();
        pk.setCourseId(courseId);
        pk.setStudentId(studentId);

        StudentCoursesEntity studentCoursesEntity = new StudentCoursesEntity();
        studentCoursesEntity.setPk(pk);
        studentCoursesEntity.setEnrollDate(LocalDateTime.now());
        studentCoursesEntity.setStatus(Constants.ACTIVE);

        StudentCoursesEntity savedStudentCourse = studentCoursesRepo.save(studentCoursesEntity);

        StudentCourse studentCourse = modelConverter.map(savedStudentCourse, StudentCourse.class);

        return Response.success(studentCourse);
    }

    @SneakyThrows
    public ResponseEntity<StudentTest> submitTests(StudentTest studentTest, TestType testType) {
        if (!courseService.courseExistsById(studentTest.getStudentId())) {
            throw new ValidationException(MsgConstants.INVALID_COURSE_ID);
        } else if (!studentsRepo.existsById(studentTest.getStudentId())) {
            throw new ValidationException(MsgConstants.STUDENT_NOT_AVAILABLE);
        } else if (studentTest.getModuleId() != 0 && !courseService.moduleExistsById(studentTest.getModuleId())) {
            throw new ValidationException(MsgConstants.MODULE_NOT_AVAILABLE);
        }

        StudentTestsEntity studentTestsEntity = modelConverter.map(studentTest, StudentTestsEntity.class);
        studentTestsEntity.setTestType(testType.getType());
        studentTestsEntity.setTestScore((double) random.nextInt(35, 101));

        StudentTestsEntity savedStudentTest = studentTestsRepo.save(studentTestsEntity);

        studentTest = mapEntityToModel(savedStudentTest);

        return Response.success(studentTest);
    }

    @SneakyThrows
    public ResponseEntity<Student> getStudentProgressData(int studentId) {
        StudentsEntity studentsEntity = studentsRepo.findById(studentId).
                orElseThrow(new ValidationException(MsgConstants.STUDENT_NOT_AVAILABLE));

        Student student = mapEntityToModel(studentsEntity);

        List<StudentTestsEntity> studentTestsEntities = studentTestsRepo.findAllByStudentId(studentId);

        if (!studentTestsEntities.isEmpty()) {
            List<StudentTest> studentTests = studentTestsEntities.stream()
                    .map(this::mapEntityToModel)
                    .toList();

            student.setStudentTests(studentTests);
        }

        return Response.success(student);
    }

    private Student mapEntityToModel(StudentsEntity studentsEntity) {
        return modelConverter.map(studentsEntity, Student.class);
    }

    private StudentTest mapEntityToModel(StudentTestsEntity studentTestsEntity) {
        return modelConverter.map(studentTestsEntity, StudentTest.class);
    }
}
