package com.edtech.elearning.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ep_student_tests")
@EntityListeners(AuditingEntityListener.class)
public class StudentTestsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_attempt_id", nullable = false)
    private Integer testAttemptId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "course_id", nullable = false)
    private Integer courseId;

    @Column(name = "module_id")
    private Integer moduleId;

    @Column(name = "test_id", nullable = false)
    private Integer testId;

    @Column(name = "test_type", nullable = false)
    private Integer testType;

    @Column(name = "test_start_time")
    private LocalDateTime testStartTime;

    @Column(name = "test_end_time")
    private LocalDateTime testEndTime;

    @Column(name = "test_data")
    private String testData;

    @Column(name = "test_score", nullable = false)
    private Double testScore;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "status")
    private Integer status;

    @Setter(AccessLevel.NONE)
    @Column(name = "ts", nullable = false, insertable = false, updatable = false)
    private LocalDateTime ts;

    @Setter(AccessLevel.NONE)
    @CreatedDate
    @Column(name = "crt_at", nullable = false, updatable = false)
    private LocalDateTime crtAt;

    @Setter(AccessLevel.NONE)
    @CreatedBy
    @Column(name = "crt_by", nullable = false, updatable = false)
    private String crtBy;

    @Setter(AccessLevel.NONE)
    @LastModifiedDate
    @Column(name = "upd_at", nullable = false)
    private LocalDateTime updAt;

    @Setter(AccessLevel.NONE)
    @LastModifiedBy
    @Column(name = "upd_by", nullable = false)
    private String updBy;

}
