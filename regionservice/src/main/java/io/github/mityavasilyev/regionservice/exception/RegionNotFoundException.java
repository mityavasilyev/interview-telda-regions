package io.github.mityavasilyev.regionservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegionNotFoundException extends RuntimeException {

    private String regionCode;

    public RegionNotFoundException() {
    }

    public RegionNotFoundException(String message) {
        super(message);
    }

    public RegionNotFoundException(String message, String regionCode) {
        super(message);
        this.regionCode = regionCode;
    }

    public RegionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
