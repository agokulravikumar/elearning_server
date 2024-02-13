package com.edtech.elearning.model;

import com.edtech.elearning.constants.MsgConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseModule {

    @Schema(description = "Unique ID of the Module. Not required in Create as it will be Auto Generated")
    private int moduleId;

    @Schema(description = "Course ID to which the module is Linked")
    private int courseId;

    @Schema(description = "Title of the Module. Max length allowed is 255")
    @NotBlank(message = MsgConstants.INVALID_MODULE_TITLE)
    @Size(max = 255, message = MsgConstants.TITLE_LENGTH_HIGH)
    private String moduleTitle;

    @Schema(description = "Description of the Module")
    private String moduleDesc;

    @Schema(description = "Starting Date of the Module if applicable")
    private LocalDateTime startDate;

    @Schema(description = "End Date of the Module if applicable")
    private LocalDateTime endDate;

    @Schema(description = "Total Duration of the Module")
    private String duration;

    @Schema(description = "Status of the Module")
    private int status = 1;

}
