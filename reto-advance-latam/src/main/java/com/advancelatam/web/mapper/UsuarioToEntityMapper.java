package com.advancelatam.web.mapper;

import com.advancelatam.common.builder.UsuarioBuilder;
import com.advancelatam.data.entities.security.Usuario;
import com.advancelatam.common.dto.UsuarioTo;

public final class UsuarioToEntityMapper {

    private UsuarioToEntityMapper() {
    }

    public static Usuario convertToUsuario(UsuarioTo usuarioTo, Long idUsuarioCrea) {
        return UsuarioBuilder.newBuilder()
                .username(usuarioTo.getUsername())
                .usernameCrea(idUsuarioCrea)
                .build();
    }
}
