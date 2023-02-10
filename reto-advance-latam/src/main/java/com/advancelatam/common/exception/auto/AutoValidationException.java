package com.advancelatam.common.exception.auto;

public class AutoValidationException extends AutoException {
    public AutoValidationException() {
        super();
    }

    public AutoValidationException(String message) {
        super(message);
    }

    public AutoValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
