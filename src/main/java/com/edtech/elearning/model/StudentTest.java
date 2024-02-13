package com.edtech.elearning.model;

import com.edtech.elearning.enums.TestType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentTest {

    @Schema(description = "Attempt ID of the Test. Will be Auto Generated")
    private int testAttemptId;

    @Schema(description = "Unique ID of the Student")
    private int studentId;

    @Schema(description = "Unique ID of the Course")
    private int courseId;

    @Schema(description = "Unique ID of the Module if applicable")
    private int moduleId;

    @Schema(description = "Unique ID of the Test")
    private int testId;

    @Schema(description = "Type of the Test", allowableValues = {"1 - Quiz", "2 - Assignment"})
    private int testType;

    @Schema(description = "Start Time of the Test")
    private LocalDateTime testStartTime;

    @Schema(description = "End Time of the Test")
    private LocalDateTime testEndTime;

    @Schema(description = "Data to be Submitted for the Test")
    private String testData;

    @Schema(description = "Score for the Test")
    private double testScore;

    @Schema(description = "Remarks for the test if any")
    private String remarks;

    @Schema(description = "Status of the Test")
    private int status = 1;
}
