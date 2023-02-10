package com.advancelatam.bussines.services.security;

import com.advancelatam.common.util.Roles;
import com.advancelatam.data.entities.security.Usuario;
import com.advancelatam.data.enums.EntityStateEnum;
import com.advancelatam.data.repositories.security.UsuarioRepository;
import com.advancelatam.data.repositories.security.UsuarioRolRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final MessageSource messageSource;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioRolRepository usuarioRolRepository, MessageSource messageSource) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.messageSource = messageSource;
    }

    @Transactional
    @Secured(Roles.ROLE_ADMIN)
    public Usuario createUser(Usuario usuario) {
        final Optional<Usuario> result = usuarioRepository.findByUsername(usuario.getUsername());

        if (result.isEmpty()) {
            return usuarioRepository.save(usuario);
        }

        return result.get();
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN})
    public void deleteUsuario(Long userId, Long idUsuarioElimina) {
        Usuario usuario = getUserById(userId);
        usuario.setEstado(EntityStateEnum.INACTIVO.getEstado());
        usuario.setFechaModifica(new Date());
        usuario.setUsuarioModifica(idUsuarioElimina);
        usuarioRepository.save(usuario);
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN})
    public void activarUsuario(Long userId, Long idUsuarioElimina) {
        Usuario usuario = getUserById(userId);
        usuario.setEstado(EntityStateEnum.ACTIVO.getEstado());
        usuario.setFechaModifica(new Date());
        usuario.setUsuarioModifica(idUsuarioElimina);
        usuarioRepository.save(usuario);
    }

    @Secured(Roles.ROLE_ADMIN)
    public Usuario getUserById(Long userId) {
        return usuarioRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format(messageSource.getMessage(
                        "{service.usuario.no.existe}", null, null,
                        LocaleContextHolder.getLocale().stripExtensions()), userId)));
    }

    @Secured({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public Usuario getUserByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format(messageSource.getMessage("service.usuario.no.existe", null, null,
                                LocaleContextHolder.getLocale().stripExtensions()), username)));
    }

    @Secured({Roles.ROLE_ADMIN})
    public Page<Usuario> getUsuarios(int page, int size) {
        return usuarioRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "username")));
    }

    @Secured({Roles.ROLE_ADMIN})
    public List<Usuario> getUsuariosByRol(String rol) {
        return usuarioRolRepository.findUsersByRol(rol);
    }
}
