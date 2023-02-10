package com.advancelatam.common.exception.auto;

public class AutoNotFoundException extends AutoException {
    public AutoNotFoundException() {
        super();
    }

    public AutoNotFoundException(String message) {
        super(message);
    }

    public AutoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
