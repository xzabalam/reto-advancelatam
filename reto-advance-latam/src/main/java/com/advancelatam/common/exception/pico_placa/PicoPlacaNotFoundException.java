package com.advancelatam.common.exception.pico_placa;

import com.advancelatam.common.exception.auto.AutoException;

public class PicoPlacaNotFoundException extends AutoException {
    public PicoPlacaNotFoundException() {
        super();
    }

    public PicoPlacaNotFoundException(String message) {
        super(message);
    }

    public PicoPlacaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
