package io.github.mityavasilyev.regionservice.exception;

import lombok.Getter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Designed to standardize error response bodies
 *
 * @param <T> Object that will be stored in response
 */
@Getter
public class ErrorResponse<T> {
    private static final String ERROR_HEADER = "failure";
    private final ErrorDTO<T> response;

    public ErrorResponse(ErrorDTO<T> response) {
        this.response = response;
    }

    public ErrorResponse(String errorMessage) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        this.response = new ErrorDTO<>(
                builder.build().toUri(),
                ERROR_HEADER,
                errorMessage,
                null);
    }

    public ErrorResponse(String errorMessage, T payload) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        this.response = new ErrorDTO<>(
                builder.build().toUri(),
                ERROR_HEADER,
                errorMessage,
                payload);
    }

    public ErrorResponse(String errorMessage, String status, T payload) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        this.response = new ErrorDTO<>(
                builder.build().toUri(),
                status,
                errorMessage,
                payload);
    }

    public record ErrorDTO<T>(URI path, String status, String errorMessage, T payload) {

    }
}
