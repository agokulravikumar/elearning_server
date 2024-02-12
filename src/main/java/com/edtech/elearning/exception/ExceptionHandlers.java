package com.edtech.elearning.exception;

import com.edtech.elearning.constants.MsgConstants;
import com.edtech.elearning.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Response> handleValidationException(Throwable ex, WebRequest request) {
        log.info("Validation Failure Exception", ex);
        return Response.failure(ex.getMessage());
    }

    @ExceptionHandler({UnAuthorizedException.class, AuthException.class, AuthenticationException.class})
    public ResponseEntity<Response> handleUnAuthorizedException(Exception ex, WebRequest request) {
        log.info("UnAuthorized Exception", ex);
        return Response.unauthorized(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGeneralException(Exception ex, WebRequest request) {
        if (ex.getCause() instanceof ValidationException) {
            return handleValidationException(ex.getCause(), request);
        }

        log.error("Exception occurred : ", ex);

        return Response.error(MsgConstants.GEN_ERROR_MSG, getRootCauseMsg(ex));
    }

    public static Throwable getRootCause(Throwable ex) {
        if (ex == null) {
            return null;
        } else if (ex.getCause() == null || ex.getCause() == ex) {
            return ex;
        } else {
            return getRootCause(ex.getCause());
        }
    }

    public static String getRootCauseMsg(Throwable ex) {
        if (ex == null) {
            return null;
        }

        if (ex.getCause() == null || ex.getCause() == ex) {
            if (ex.getMessage() == null) {
                return ex.toString();
            }
            return ex.getMessage();
        } else {
            Throwable rootCause = getRootCause(ex.getCause());
            return (rootCause == null) ? null : rootCause.getMessage();
        }
    }

}
