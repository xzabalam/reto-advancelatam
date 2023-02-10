package com.advancelatam.common.enumeration;

public enum EstadoEnum {
    ACTIVO("A"), INACTIVO("I"), CREADO("C");

    private final String estado;

    EstadoEnum(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
