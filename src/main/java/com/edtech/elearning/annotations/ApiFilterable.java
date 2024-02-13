package com.edtech.elearning.annotations;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Parameter(in = ParameterIn.QUERY, description = "Filter Criteria in the format: property,condition,value. " +
        "Multiple filter criteria are supported", name = "filter", content = @Content(schema = @Schema(type = "string", defaultValue = "")))
public @interface ApiFilterable {
}
