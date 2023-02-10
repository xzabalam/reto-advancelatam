package com.advancelatam.data.repositories.auto;

import com.advancelatam.data.entities.auto.TipoAuto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoAutoRepository extends JpaRepository<TipoAuto, Long> {
    Page<TipoAuto> findAllByEstado(Pageable pageable, String estado);

    List<TipoAuto> findAllByEstado(String estado);

    Optional<TipoAuto> findByTipoAndEstado(String tipo, String estado);
}
