package com.edtech.elearning.exception;

public class UnAuthorizedException extends Exception {

    public UnAuthorizedException(String exceptionMsg) {
        super(exceptionMsg);
    }

    public UnAuthorizedException(String exceptionMsg, Throwable cause) {
        super(exceptionMsg, cause);
    }

}
