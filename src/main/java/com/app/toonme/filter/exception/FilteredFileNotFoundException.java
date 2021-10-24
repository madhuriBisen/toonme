package com.app.toonme.filter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FilteredFileNotFoundException extends RuntimeException {
    public FilteredFileNotFoundException(String message) {
        super(message);
    }

    public FilteredFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
