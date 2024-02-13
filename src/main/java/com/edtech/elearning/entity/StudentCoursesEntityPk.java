package com.edtech.elearning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class StudentCoursesEntityPk implements Serializable {

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "course_id", nullable = false)
    private Integer courseId;

}
