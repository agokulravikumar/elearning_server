package com.edtech.elearning.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpecialSymbol {

    TILDE("~", "%7E"),
    EQUALS(":", "%3A"),
    GREATER_THAN(">", "%3E"),
    GREATER_THAN_OR_EQUAL(">=", "%3E%3D"),
    LESS_THAN("<", "%3C"),
    LESS_THAN_OR_EQUAL("<=", "%3C%3D"),
    LIKE("%", "%25"),
    NOT_LIKE("!%", "%21%25"),
    IN("!", "%21"),
    NOT_IN("!!", "%21%21");

    private String plain;
    private String encoded;

}
