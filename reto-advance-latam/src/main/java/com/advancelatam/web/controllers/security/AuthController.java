package com.advancelatam.web.controllers.security;

import com.advancelatam.bussines.services.security.UsuarioService;
import com.advancelatam.data.entities.security.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/auth")
    public ResponseEntity<Usuario> validateLogin(Authentication authentication) {
        Usuario usuario = usuarioService.getUserByUsername(authentication.getName());
        usuario.setRoles(authentication.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList()));
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
