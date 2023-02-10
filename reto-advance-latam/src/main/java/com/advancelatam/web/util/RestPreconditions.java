package com.advancelatam.web.util;

import com.advancelatam.web.exceptions.MyResourceNotFoundException;

public final class RestPreconditions {
    private RestPreconditions() {
        throw new AssertionError();
    }

    public static <T> T checkNull(final T resource) {
        if (resource == null) {
            throw new MyResourceNotFoundException("El parámetro de entrada no puede ser nulo.");
        }

        return resource;
    }

    public static <T> T checkEmptyString(final T resource) {
        checkNull(resource);

        if (resource instanceof String) {
            if (((String) resource).trim().isEmpty()) {
                throw new MyResourceNotFoundException("Se esperaba un valor.");
            }
        }

        return resource;
    }
}
