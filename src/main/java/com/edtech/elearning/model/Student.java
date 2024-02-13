package com.edtech.elearning.model;

import com.edtech.elearning.constants.MsgConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class Student {

    @Schema(description = "Unique ID of the Student. Not required in Create, will be Auto Generated")
    private int studentId;

    @Schema(description = "Name of the Student. Max Length is 100")
    @NotBlank(message = MsgConstants.STUDENT_NAME_EMPTY)
    @Max(value = 100, message = MsgConstants.STUDENT_NAME_EXCEED)
    private String studentName;

    @Schema(description = "Email ID of the Student")
    private String emailId;

    @Schema(description = "Mobile No of the Student")
    private String mobileNo;

    @Schema(description = "Address of the Student")
    private String address;

    @Schema(description = "City of the Student")
    private String city;

    @Schema(description = "State of the Student")
    private String state;

    @Schema(description = "Country of the Student")
    private String country;

    @Schema(description = "Status of the Student")
    private int status = 1;

    @Schema(description = "Progress of Student Tests")
    private List<StudentTest> studentTests;

}
