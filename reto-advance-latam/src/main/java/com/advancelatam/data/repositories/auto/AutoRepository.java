package com.advancelatam.data.repositories.auto;

import com.advancelatam.data.entities.auto.Auto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
    Optional<Auto> findByPlaca(String placa);

    Page<Auto> findAllByEstado(Pageable pageable, String estado);

    List<Auto> findAllByEstado(String estado);
}
