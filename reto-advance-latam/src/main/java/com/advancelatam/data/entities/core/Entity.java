package com.advancelatam.data.entities.core;

import java.io.Serializable;

public interface Entity<T> extends Serializable {

    T getId();

    void setId(T id);

    String getEstado();

    void setEstado(String estado);
}
