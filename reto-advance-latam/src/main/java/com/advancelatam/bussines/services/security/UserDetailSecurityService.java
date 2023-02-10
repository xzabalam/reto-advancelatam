package com.advancelatam.bussines.services.security;

import com.advancelatam.data.entities.security.Permiso;
import com.advancelatam.data.entities.security.Usuario;
import com.advancelatam.data.repositories.security.UsuarioRepository;
import com.advancelatam.data.repositories.security.UsuarioRolRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserDetailSecurityService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository userInRoleRepository;

    public UserDetailSecurityService(UsuarioRepository usuarioRepository, UsuarioRolRepository userInRoleRepository) {
        this.usuarioRepository = usuarioRepository;
        this.userInRoleRepository = userInRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Usuario> optionalUser = usuarioRepository.getByUsername(username);

        if (optionalUser.isPresent()) {
            final Usuario usuario = optionalUser.get();
            final List<Permiso> listaPermiso = userInRoleRepository.findByUsername(usuario.getUsername());
            final String[] roles = listaPermiso.stream().map(permiso -> permiso.getRol().getNombre())
                    .toArray(String[]::new);
            return User.withUsername(usuario.getUsername()).password(usuario.getPassword()).roles(roles).build();
        }

        throw new UsernameNotFoundException(String.format("{security.usuario.no.encontrado}", username));
    }
}
