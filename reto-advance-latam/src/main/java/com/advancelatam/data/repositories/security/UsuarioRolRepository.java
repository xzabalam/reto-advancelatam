package com.advancelatam.data.repositories.security;

import com.advancelatam.data.entities.security.Permiso;
import com.advancelatam.data.entities.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRolRepository extends JpaRepository<Permiso, Long> {

    @Query("select ur from Permiso ur where ur.usuario.username = :username ")
    List<Permiso> findByUsername(@Param("username") String username);

    @Query("select ur from Permiso ur where ur.usuario.username = :username and ur.rol.nombre = :rol ")
    Optional<Permiso> findByUsernameAndRol(@Param("username") String username, @Param("rol") String rol);

    @Query("select ur.usuario from Permiso ur where ur.rol.nombre = :rol ")
    List<Usuario> findUsersByRol(@Param("rol") String rol);

    List<Permiso> findByUsuarioId(Long usuarioId);
}
