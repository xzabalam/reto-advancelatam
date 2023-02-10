package com.advancelatam.common.builder;

import com.advancelatam.data.entities.security.Rol;
import com.advancelatam.data.enums.EntityStateEnum;

import java.util.Date;

public class RolBuilder {

    private static Rol rol;

    public static RolBuilder newBuilder() {
        rol = new Rol();
        return new RolBuilder();
    }

    public Rol build() {
        rol.setFechaCrea(new Date());
        rol.setEstado(EntityStateEnum.ACTIVO.getEstado());
        return rol;
    }


    public RolBuilder nombre(String nombre) {
        rol.setNombre(nombre);
        return this;
    }

    public RolBuilder usuarioCrea(Long idUsuarioCrea) {
        rol.setUsuarioCrea(idUsuarioCrea);
        return this;
    }
}
