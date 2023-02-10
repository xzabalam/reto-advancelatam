package com.advancelatam.data.repositories.pico_placa;

import com.advancelatam.data.entities.pico_placa.PicoPlaca;
import com.advancelatam.data.enums.DiasSemanaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PicoPlacaRepository extends JpaRepository<PicoPlaca, Long> {
    Page<PicoPlaca> findAllByEstado(Pageable pageable, String estado);

    List<PicoPlaca> findAllByEstadoOrderByDiaSemanaAscHoraInicioAsc(String estado);

    List<PicoPlaca> findByDiaSemanaAndEstado(DiasSemanaEnum diasSemanaEnum, String estado);
}
