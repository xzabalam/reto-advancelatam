package com.advancelatam.web.controllers.security;

import com.advancelatam.bussines.services.security.UsuarioService;
import com.advancelatam.common.util.Roles;
import com.advancelatam.data.entities.security.Usuario;
import com.advancelatam.common.dto.UsuarioTo;
import com.advancelatam.web.mapper.UsuarioToEntityMapper;
import com.advancelatam.web.util.RestPreconditions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @GetMapping("/username/{username}")
    @Operation(summary = "Permite obtener el usuario por el username, Roles permitidos Administrador y Usuario")
    public ResponseEntity<Usuario> getUsuarioByUsername(@PathVariable("username") String username) {
        RestPreconditions.checkEmptyString(username);

        final Usuario usuario = usuarioService.getUserByUsername(username);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @GetMapping("/all")
    @Operation(summary = "Permite obtener el listado paginado de usuarios, Roles permitidos Administrador.")
    public ResponseEntity<Page<Usuario>> getUsuarios(
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "size", defaultValue = "500") int size
    ) {
        final Page<Usuario> usuarios = usuarioService.getUsuarios(page, size);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @PostMapping
    @Operation(summary = "Permite crear un nuevo usuario")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody @Valid UsuarioTo usuarioTo, Authentication authentication) {
        RestPreconditions.checkNull(usuarioTo);
        Usuario usuarioCrea = usuarioService.getUserByUsername(authentication.getName());
        Usuario usuario = UsuarioToEntityMapper.convertToUsuario(usuarioTo, usuarioCrea.getId());
        return new ResponseEntity<>(usuarioService.createUser(usuario), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @DeleteMapping("/{userid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Permite eliminar logicamente un usuario con base en su id")
    public void eliminarUsuario(@PathVariable("userid") Long userId, Authentication authentication) {
        RestPreconditions.checkNull(userId);
        Usuario usuarioElimina = usuarioService.getUserByUsername(authentication.getName());
        usuarioService.deleteUsuario(userId, usuarioElimina.getId());
    }

    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    @PutMapping("/{userid}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Permite activar logicamente un usuario con base en su id")
    public void activarUsuario(@PathVariable("userid") Long userId, Authentication authentication) {
        RestPreconditions.checkNull(userId);
        Usuario usuarioElimina = usuarioService.getUserByUsername(authentication.getName());
        usuarioService.activarUsuario(userId, usuarioElimina.getId());
    }
}
