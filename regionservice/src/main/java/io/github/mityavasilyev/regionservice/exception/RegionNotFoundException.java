package io.github.mityavasilyev.regionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegionNotFoundException extends RuntimeException {
    public RegionNotFoundException() {
    }

    public RegionNotFoundException(String message) {
        super(message);
    }

    public RegionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
