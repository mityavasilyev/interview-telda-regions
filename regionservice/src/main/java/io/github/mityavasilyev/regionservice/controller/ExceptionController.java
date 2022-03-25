package io.github.mityavasilyev.regionservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    /**
     * @param errorMessage Message to put into response body
     * @param extraObject  Some additional info if needed
     * @return Map that contains error info
     */
    public static Map<String, String> buildErrorBody(String errorMessage, Object extraObject) {
        Map<String, String> body = new HashMap<>();

        body.put("error_message", errorMessage);
        try {
            if (extraObject != null) body.put("more_info", new ObjectMapper().writeValueAsString(extraObject));
        } catch (JsonProcessingException e) {
            log.error("Failed to put extra payload into exception message: {}", extraObject);
        }
        return body;
    }

    public static Map<String, String> buildErrorBody(String errorMessage) {
        return buildErrorBody(errorMessage, null);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleDataIntegrityViolation(Exception ex, WebRequest request) {
        log.warn("An error has occurred: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildErrorBody(ex.getMessage()));
    }
}
