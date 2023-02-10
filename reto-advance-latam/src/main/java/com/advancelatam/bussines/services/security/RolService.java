package com.advancelatam.bussines.services.security;

import com.advancelatam.common.util.Roles;
import com.advancelatam.data.entities.security.Rol;
import com.advancelatam.data.repositories.security.RolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RolService {
    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN})
    public Rol createRol(Rol rol) {
        final Optional<Rol> result = rolRepository.findByNombre(rol.getNombre());

        if (result.isPresent()) {
            return result.get();
        }

        return rolRepository.save(rol);
    }


    @Transactional
    @Secured({Roles.ROLE_ADMIN})
    public void deleteRol(Long rolId) {
        final Optional<Rol> result = rolRepository.findById(rolId);

        if (result.isPresent()) {
            rolRepository.delete(result.get());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("{service.rol.no.existe}", rolId));
    }

    @Transactional
    @Secured({Roles.ROLE_ADMIN})
    public Rol updateRol(Long rolId, Rol rol) {
        final Optional<Rol> result = rolRepository.findById(rolId);

        if (result.isPresent()) {
            return rolRepository.save(rol);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("{service.rol.no.existe}", rolId));
    }

    public Page<Rol> getRoles(int page, int size) {
        return rolRepository.findAll(PageRequest.of(page, size));
    }
}
