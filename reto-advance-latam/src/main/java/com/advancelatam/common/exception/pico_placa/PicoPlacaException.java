package com.advancelatam.common.exception.pico_placa;

public class PicoPlacaException extends RuntimeException {
    public PicoPlacaException() {
        super();
    }

    public PicoPlacaException(String message) {
        super(message);
    }

    public PicoPlacaException(String message, Throwable cause) {
        super(message, cause);
    }
}

