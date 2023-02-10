package com.advancelatam.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class AutoTo {
    private Long id;

    @NotNull(message = "{pico.placa.auto.placa.notnull}")
    @Size(min = 1, max = 10, message = "{pico.placa.auto.placa.size}")
    private String placa;

    @NotNull(message = "{pico.placa.auto.modelo.notnull}")
    @Size(min = 1, max = 500, message = "{pico.placa.auto.modelo.size}")
    private String modelo;

    @NotNull(message = "{pico.placa.auto.chasis.notnull}")
    @Size(min = 1, max = 50, message = "{pico.placa.auto.chasis.size}")
    private String chasis;

    @NotNull(message = "{pico.placa.auto.color.notnull}")
    @Size(min = 1, max = 50, message = "{pico.placa.auto.color.size}")
    private String color;

    @NotNull(message = "{pico.placa.auto.tipo.notnull}")
    private Long idTipoAuto;
}


