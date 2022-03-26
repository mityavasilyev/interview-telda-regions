package io.github.mityavasilyev.regionservice.controller;

import io.github.mityavasilyev.regionservice.exception.MissingRegionDataException;
import io.github.mityavasilyev.regionservice.exception.RegionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {RegionNotFoundException.class})
    public ResponseEntity<ErrorResponse<String>> handleRegionNotFound(
            RegionNotFoundException exception,
            WebRequest request) {
        log.warn("Controller error: {} [{}]", exception.getMessage(), exception.getClass());

        return ResponseEntity.badRequest().body(
                new ErrorResponse<>(exception.getMessage(), exception.getRegionCode()));
    }

    @ExceptionHandler(value = {MissingRegionDataException.class})
    public ResponseEntity<ErrorResponse<String>> handleMissingRegionData(
            MissingRegionDataException exception,
            WebRequest request) {
        log.warn("Controller error: {} [{}]", exception.getMessage(), exception.getClass());

        return ResponseEntity.badRequest().body(
                new ErrorResponse<>(exception.getMessage()));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse<String>> handleIllegalArgument(
            IllegalArgumentException exception,
            WebRequest request) {
        log.warn("Controller error: {} [{}]", exception.getMessage(), exception.getClass());

        return ResponseEntity.badRequest().body(
                new ErrorResponse<>(exception.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleDataIntegrityViolation(Exception ex, WebRequest request) {
        log.warn("Internal error has occurred: {}", ex.getMessage());

        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse<String>("Bruh, some trouble on server side"));
    }
}
