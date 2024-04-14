package com.movies.catalog.exception;

public class MovieAlreadyExistsException extends RuntimeException {
    public MovieAlreadyExistsException() {
        super();
    }

    public MovieAlreadyExistsException(final String message) {
        super(message);
    }
}
