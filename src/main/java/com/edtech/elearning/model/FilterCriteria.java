package com.edtech.elearning.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterCriteria {

    private double id;
    private String key;
    private String operation;
    private String value;

    public FilterCriteria(String key, String operation, String value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
