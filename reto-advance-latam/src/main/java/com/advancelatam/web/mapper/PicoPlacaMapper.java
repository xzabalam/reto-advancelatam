package com.advancelatam.web.mapper;

import com.advancelatam.common.builder.PicoPlacaBuilder;
import com.advancelatam.data.entities.pico_placa.PicoPlaca;
import com.advancelatam.common.dto.PicoPlacaTo;

public final class PicoPlacaMapper {
    private PicoPlacaMapper() {
    }

    public static PicoPlaca toEntity(PicoPlacaTo picoPlacaTo, Long idUsuarioCrea) {
        return PicoPlacaBuilder.newBuilder()
                .diaSemana(picoPlacaTo.getDiaSemana())
                .horaInicio(picoPlacaTo.getHoraInicio())
                .horaFin(picoPlacaTo.getHoraFin())
                .resticcionUltimoDigito(picoPlacaTo.getResticcionUltimoDigito())
                .usuarioCrea(idUsuarioCrea)
                .build();
    }

    public static PicoPlacaTo toTo(PicoPlaca picoPlaca) {
        return PicoPlacaTo.builder()
                .diaSemana(picoPlaca.getDiaSemana())
                .horaInicio(picoPlaca.getHoraInicio())
                .horaFin(picoPlaca.getHoraFin())
                .resticcionUltimoDigito(picoPlaca.getResticcionUltimoDigito())
                .build();
    }
}
