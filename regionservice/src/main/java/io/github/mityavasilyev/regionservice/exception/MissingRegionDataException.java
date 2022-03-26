package io.github.mityavasilyev.regionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MissingRegionDataException extends RuntimeException {
    public MissingRegionDataException() {
    }

    public MissingRegionDataException(String message) {
        super(message);
    }

    public MissingRegionDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
