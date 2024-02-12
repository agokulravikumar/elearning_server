package com.edtech.elearning.exception;

import java.util.function.Supplier;

public class ValidationException extends AppException implements Supplier<ValidationException> {

    public ValidationException(String exceptionMsg) {
        super(exceptionMsg);
    }

    public ValidationException(String format, Object... args) {
        super(String.format(format, args));
    }

    @Override
    public ValidationException get() {
        return new ValidationException(this.getMessage());
    }
}
