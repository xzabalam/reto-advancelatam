package com.advancelatam.common.dto;

import com.advancelatam.data.enums.DiasSemanaEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class PicoPlacaTo {
    private Long id;

    @NotNull(message = "{pico.placa.dia.semana.notnull}")
    private DiasSemanaEnum diaSemana;

    @NotNull(message = "{pico.placa.hora.inicio.notnull}")
    private LocalTime horaInicio;

    @NotNull(message = "{pico.placa.hora_fin.notnull}")
    private LocalTime horaFin;

    private List<Byte> resticcionUltimoDigito;
}
