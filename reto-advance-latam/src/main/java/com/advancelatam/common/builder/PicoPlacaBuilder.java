package com.advancelatam.common.builder;

import com.advancelatam.data.entities.pico_placa.PicoPlaca;
import com.advancelatam.data.enums.DiasSemanaEnum;
import com.advancelatam.data.enums.EntityStateEnum;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class PicoPlacaBuilder {
    private static PicoPlaca picoPlaca;

    public static PicoPlacaBuilder newBuilder() {
        picoPlaca = new PicoPlaca();
        return new PicoPlacaBuilder();
    }

    public PicoPlaca build() {
        picoPlaca.setFechaCrea(new Date());
        picoPlaca.setEstado(EntityStateEnum.ACTIVO.getEstado());
        return picoPlaca;
    }

    public PicoPlacaBuilder diaSemana(DiasSemanaEnum diasSemanaEnum) {
        picoPlaca.setDiaSemana(diasSemanaEnum);
        return this;
    }

    public PicoPlacaBuilder horaInicio(LocalTime horaInicio) {
        picoPlaca.setHoraInicio(horaInicio);
        return this;
    }

    public PicoPlacaBuilder horaFin(LocalTime horaFin) {
        picoPlaca.setHoraFin(horaFin);
        return this;
    }

    public PicoPlacaBuilder resticcionUltimoDigito(List<Byte> resticcionUltimoDigito) {
        picoPlaca.setResticcionUltimoDigito(resticcionUltimoDigito);
        return this;
    }

    public PicoPlacaBuilder usuarioCrea(Long idUsuarioCrea) {
        picoPlaca.setUsuarioCrea(idUsuarioCrea);
        return this;
    }
}
