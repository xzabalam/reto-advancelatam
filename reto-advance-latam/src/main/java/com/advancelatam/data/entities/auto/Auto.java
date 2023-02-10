package com.advancelatam.data.entities.auto;

import com.advancelatam.data.entities.core.impl.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.concurrent.locks.StampedLock;

@Entity
@Table(name = "auto")
@Getter
@Setter
public class Auto extends AbstractEntity<Long> {

    @Transient
    private final StampedLock stampedLock;

    @NotNull(message = "{pico.placa.auto.placa.notnull}")
    @Size(min = 1, max = 10, message = "{pico.placa.auto.placa.size}")
    @Column(name = "placa", unique = true)
    private String placa;

    @NotNull(message = "{pico.placa.auto.modelo.notnull}")
    @Size(min = 1, max = 500, message = "{pico.placa.auto.modelo.size}")
    @Column(name = "modelo")
    private String modelo;

    @NotNull(message = "{pico.placa.auto.chasis.notnull}")
    @Size(min = 1, max = 50, message = "{pico.placa.auto.chasis.size}")
    @Column(name = "chasis")
    private String chasis;

    @NotNull(message = "{pico.placa.auto.color.notnull}")
    @Size(min = 1, max = 50, message = "{pico.placa.auto.color.size}")
    @Column(name = "color")
    private String color;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_tipo_auto", referencedColumnName = "id")
    private TipoAuto tipoAuto;

    @Version
    private Long version;

    public Auto() {
        stampedLock = new StampedLock();
    }
}
