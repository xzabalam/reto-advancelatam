package com.advancelatam.bussines.services.security;

import com.advancelatam.common.util.Roles;
import com.advancelatam.data.entities.security.Permiso;
import com.advancelatam.data.entities.security.Rol;
import com.advancelatam.data.entities.security.Usuario;
import com.advancelatam.data.repositories.security.RolRepository;
import com.advancelatam.data.repositories.security.UsuarioRolRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioRolService {

    private final UsuarioRolRepository usuarioRolRepository;
    private final RolRepository rolRepository;

    public UsuarioRolService(UsuarioRolRepository usuarioRolRepository, RolRepository rolRepository) {
        this.usuarioRolRepository = usuarioRolRepository;
        this.rolRepository = rolRepository;
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN})
    public Permiso crearUsuarioRol(Usuario usuario, Rol rol, Long idUsuarioCrea) {
        final Optional<Permiso> usuarioRol = usuarioRolRepository.findByUsernameAndRol(usuario.getUsername(),
                rol.getNombre());

        if (usuarioRol.isEmpty()) {
            final Permiso permisoNuevo = new Permiso(usuario, rol);
            permisoNuevo.setUsuarioCrea(idUsuarioCrea);
            return usuarioRolRepository.save(permisoNuevo);
        }

        return usuarioRol.get();
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN})
    public void deleteUserByUsuarioId(Long usuarioId) {
        final List<Permiso> usuarioRoles = usuarioRolRepository.findByUsuarioId(usuarioId);
        usuarioRoles.forEach(permiso -> usuarioRolRepository.delete(permiso));
    }
}
