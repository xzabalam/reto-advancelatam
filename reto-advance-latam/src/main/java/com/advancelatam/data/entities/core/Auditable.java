package com.advancelatam.data.entities.core;

public interface Auditable<T> extends Dated {
    T getUsuarioCrea();

    void setUsuarioCrea(T usuarioCrea);

    T getUsuarioModifica();

    void setUsuarioModifica(T usuarioModifica);
}
