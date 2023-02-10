package com.advancelatam.bussines.services.auto;

import com.advancelatam.common.enumeration.EstadoEnum;
import com.advancelatam.common.exception.auto.AutoNotFoundException;
import com.advancelatam.data.entities.auto.TipoAuto;
import com.advancelatam.data.repositories.auto.TipoAutoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAutoService {
    private final TipoAutoRepository tipoAutoRepository;

    public TipoAutoService(TipoAutoRepository tipoAutoRepository) {
        this.tipoAutoRepository = tipoAutoRepository;
    }

    @Cacheable(value = "TIPO_AUTO")
    public List<TipoAuto> findAll() {
        return tipoAutoRepository.findAllByEstado(EstadoEnum.ACTIVO.getEstado());
    }

    public Page<TipoAuto> findAll(int page, int size) {
        return tipoAutoRepository.findAllByEstado(PageRequest.of(page, size), EstadoEnum.ACTIVO.getEstado());
    }

    @Cacheable(value = "TIPO_AUTO_POR_TIPO", key = "#tipo")
    public TipoAuto findByTipo(String tipo) {
        return tipoAutoRepository.findByTipoAndEstado(tipo, EstadoEnum.ACTIVO.getEstado()).orElseThrow(() -> new AutoNotFoundException(
                "El " +
                        "tipo de auto " + tipo + " no existe"));
    }

    @Cacheable(value = "TIPO_AUTO_POR_ID", key = "#idTipoAuto")
    public TipoAuto findById(Long idTipoAuto) {
        return tipoAutoRepository.findById(idTipoAuto).orElseThrow(() -> new AutoNotFoundException(
                "El tipo de auto no existe para el id" + idTipoAuto));
    }
}
