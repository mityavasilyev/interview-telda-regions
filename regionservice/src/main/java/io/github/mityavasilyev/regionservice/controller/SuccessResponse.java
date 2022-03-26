package io.github.mityavasilyev.regionservice.controller;

import lombok.Getter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Designed to standardize successful response bodies
 *
 * @param <T> Object that will be stored in response
 */
@Getter
public class SuccessResponse<T> {
    private static final String SUCCESS_HEADER = "success";
    private final SuccessDTO<T> response;

    public SuccessResponse(SuccessDTO<T> response) {
        this.response = response;
    }

    public SuccessResponse(T bodyObject) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        this.response = new SuccessDTO<>(builder.build().toUri(), SUCCESS_HEADER, bodyObject);
    }

    public SuccessResponse(T bodyObject, String status) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        this.response = new SuccessDTO<>(builder.build().toUri(), status, bodyObject);
    }

    public SuccessResponse(String status, URI uri) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        this.response = new SuccessDTO<>(builder.build().toUri(), status, null);
    }

    public SuccessResponse(T bodyObject, String status, URI uri) {
        this.response = new SuccessDTO<>(uri, status, bodyObject);
    }

    public record SuccessDTO<T>(URI path, String status, T bodyObject) {

    }
}
