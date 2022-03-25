package io.github.mityavasilyev.regionservice.exception;

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
