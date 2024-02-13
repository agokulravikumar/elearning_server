package com.edtech.elearning.model;

import com.edtech.elearning.constants.MsgConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Course {

    @Schema(description = "Unique ID of the Course. Not required in Create as it will be Auto Generated")
    private int courseId;

    @Schema(description = "Title of the Course. Max Length can be 255")
    @NotBlank(message = MsgConstants.INVALID_COURSE_TITLE)
    @Size(max = 255, message = MsgConstants.TITLE_LENGTH_HIGH)
    private String courseTitle;

    @Schema(description = "Description of the Course")
    private String courseDesc;

    @Schema(description = "Starting Date of the Course if applicable")
    private LocalDateTime startDate;

    @Schema(description = "End Date of the Course if applicable")
    private LocalDateTime endDate;

    @Schema(description = "Total Duration of the Course")
    private String duration;

    @Schema(description = "Status of the Course")
    private int status = 1;

}
