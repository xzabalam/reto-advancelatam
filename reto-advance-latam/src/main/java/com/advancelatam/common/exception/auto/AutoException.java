package com.advancelatam.common.exception.auto;

public class AutoException extends RuntimeException {
    public AutoException() {
        super();
    }

    public AutoException(String message) {
        super(message);
    }

    public AutoException(String message, Throwable cause) {
        super(message, cause);
    }
}

