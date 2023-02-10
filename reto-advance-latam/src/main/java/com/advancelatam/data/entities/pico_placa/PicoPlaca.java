package com.advancelatam.data.entities.pico_placa;

import com.advancelatam.data.entities.core.impl.AbstractEntity;
import com.advancelatam.data.enums.DiasSemanaEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "pico_placa")
@Getter
@Setter
public class PicoPlaca extends AbstractEntity<Long> {
    @NotNull(message = "{pico.placa.dia.semana.notnull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "restriccion_dia")
    private DiasSemanaEnum diaSemana;

    @NotNull(message = "{pico.placa.hora.inicio.notnull}")
    @Column(name = "restriccion_hora_inicio")
    private LocalTime horaInicio;

    @NotNull(message = "{pico.placa.hora_fin.notnull}")
    @Column(name = "restriccion_hora_fin")
    private LocalTime horaFin;

    @ElementCollection
    @CollectionTable(name = "pico_placa_restriccion")
    @Column(name = "ultimo_digito", columnDefinition = "TINYINT")
    private List<Byte> resticcionUltimoDigito;
}
