package com.edtech.elearning.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TestType {
    QUIZ(1),
    ASSIGNMENT(2);

    private int type;
}
