package com.advancelatam.data.entities.auto;

import com.advancelatam.data.entities.core.impl.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tipo_auto")
@Getter
@Setter
public class TipoAuto extends AbstractEntity<Long> {

    @NotNull(message = "{pico.placa.tipo.auto.tipo.notnull}")
    @Column(name = "tipo")
    private String tipo;

    @NotNull(message = "{pico.placa.tipo.auto.descricpion.notnull}")
    @Column(name = "descripcion")
    private String descripcion;
}
