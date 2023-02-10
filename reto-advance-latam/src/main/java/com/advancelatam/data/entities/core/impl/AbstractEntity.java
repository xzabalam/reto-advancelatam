package com.advancelatam.data.entities.core.impl;

import com.advancelatam.data.entities.core.Auditable;
import com.advancelatam.data.entities.core.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity<T> implements Entity<T>, Auditable<T> {
    private static final long serialVersionUID = -412218241272244613L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    @NotNull
    @Column(name = "fecha_crea", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date fechaCrea;

    @Column(name = "fecha_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date fechaModifica;

    @Column(name = "id_usuario_crea", nullable = false)
    @JsonIgnore
    private T usuarioCrea;

    @Column(name = "id_usuario_modifica")
    @JsonIgnore
    private T usuarioModifica;

    @NotNull
    @NotEmpty
    @Column(name = "estado")
    @JsonIgnore
    private String estado;

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public Date getFechaCrea() {
        return fechaCrea;
    }

    @Override
    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    @Override
    public Date getFechaModifica() {
        return fechaModifica;
    }

    @Override
    public void setFechaModifica(Date fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public void setId(T id) {
        this.id = id;
    }

    @Override
    public T getUsuarioCrea() {
        return usuarioCrea;
    }

    @Override
    public void setUsuarioCrea(T usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    @Override
    public T getUsuarioModifica() {
        return usuarioModifica;
    }

    @Override
    public void setUsuarioModifica(T usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }


}
