package com.advancelatam.common.exception.pico_placa;

import com.advancelatam.common.exception.auto.AutoException;

public class PicoPlacaValidationException extends AutoException {
    public PicoPlacaValidationException() {
        super();
    }

    public PicoPlacaValidationException(String message) {
        super(message);
    }

    public PicoPlacaValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
