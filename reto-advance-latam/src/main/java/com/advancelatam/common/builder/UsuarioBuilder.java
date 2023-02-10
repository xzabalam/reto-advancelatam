package com.advancelatam.common.builder;

import com.advancelatam.data.entities.security.Usuario;
import com.advancelatam.data.enums.EntityStateEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

public class UsuarioBuilder {

    private static Usuario usuario;

    private static final String getEncode(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static final UsuarioBuilder newBuilder() {
        usuario = new Usuario();
        return new UsuarioBuilder();
    }

    public UsuarioBuilder username(String username) {
        usuario.setUsername(username);
        final String password = getEncode(username);
        usuario.setPassword(password);
        return this;
    }

    public Usuario build() {
        usuario.setFechaCrea(new Date());
        usuario.setEstado(EntityStateEnum.ACTIVO.getEstado());
        return usuario;
    }

    public UsuarioBuilder usernameCrea(Long idUsuarioCrea) {
        usuario.setUsuarioCrea(idUsuarioCrea);
        return this;
    }
}
