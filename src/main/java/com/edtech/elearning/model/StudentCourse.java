package com.edtech.elearning.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentCourse {

    @Schema(description = "Unique ID of the Student")
    private int studentId;

    @Schema(description = "Unique ID of the Course")
    private int courseId;

    @Schema(description = "Date of Enrollment to the Course")
    private LocalDateTime enrollDate;

    @Schema(description = "Remarks of the Enrollment if any")
    private String remarks;

    @Schema(description = "Status of the Enrollment")
    private int status = 1;

}
