package com.edtech.elearning.util;

import com.edtech.elearning.constants.Constants;
import com.edtech.elearning.enums.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.OutputStream;

@Data
@AllArgsConstructor
public class Response {

    private ResponseStatus status;
    private String message;
    private Object data;

    public static ResponseEntity success() {
        return success(Constants.SUCCESS, null);
    }

    public static ResponseEntity success(Object data) {
        return success(Constants.SUCCESS, data);
    }

    public static ResponseEntity success(String message, Object data) {
        MultiValueMap headers = new LinkedMultiValueMap<>();
        headers.add(Constants.MSG, message);

        return success(headers, data);
    }

    public static ResponseEntity success(Page page, Object data) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(Constants.TOTAL_ELEMENTS, String.valueOf(page.getTotalElements()));
        headers.add(Constants.TOTAL_PAGES, String.valueOf(page.getTotalPages()));
        headers.add(Constants.CURRENT_PAGE, String.valueOf(page.getNumber()));
        headers.add(Constants.NO_OF_ELEMENTS, String.valueOf(page.getNumberOfElements()));

        return success(headers, data);
    }

    public static ResponseEntity success(MultiValueMap<String, String> headers, Object data) {
        return ResponseEntity.status(HttpStatus.OK)
                .headers(new HttpHeaders(headers))
                .body(data);
    }

    public static ResponseEntity success(HttpHeaders headers, Object data) {
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(data);
    }

    public static ResponseEntity failure(Object data) {
        return failure(Constants.FAILURE, data);
    }

    public static ResponseEntity failure(String message, Object data) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .header(Constants.MSG, message)
                .body(data);
    }

    public static ResponseEntity error(String message, Object data) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(Constants.MSG, message)
                .body(data);
    }

    public static ResponseEntity error(Object data) {
        return error(Constants.ERROR, data);
    }

    public static ResponseEntity unauthorized(Object data) {
        return unauthorized(Constants.UNAUTHORIZED, data);
    }

    public static ResponseEntity unauthorized(String message, Object data) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(Constants.MSG, message)
                .body(data);
    }

    public static OutputStream unauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream out = response.getOutputStream();
        new ObjectMapper().writeValue(out, new Response(ResponseStatus.ERROR, message, null));
        return out;
    }

}
