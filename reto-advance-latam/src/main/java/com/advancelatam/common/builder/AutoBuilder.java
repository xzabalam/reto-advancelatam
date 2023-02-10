package com.advancelatam.common.builder;

import com.advancelatam.data.entities.auto.Auto;
import com.advancelatam.data.entities.auto.TipoAuto;
import com.advancelatam.data.enums.EntityStateEnum;

import java.util.Date;

public class AutoBuilder {
    private static Auto auto;

    public static AutoBuilder newBuilder() {
        auto = new Auto();
        return new AutoBuilder();
    }

    public Auto build() {
        auto.setFechaCrea(new Date());
        auto.setEstado(EntityStateEnum.ACTIVO.getEstado());
        return auto;
    }

    public AutoBuilder placa(String placa) {
        auto.setPlaca(placa);
        return this;
    }

    public AutoBuilder modelo(String modelo) {
        auto.setModelo(modelo);
        return this;
    }

    public AutoBuilder chasis(String chasis) {
        auto.setChasis(chasis);
        return this;
    }

    public AutoBuilder color(String color) {
        auto.setColor(color);
        return this;
    }

    public AutoBuilder tipo(TipoAuto tipo) {
        auto.setTipoAuto(tipo);
        return this;
    }

    public AutoBuilder usuarioCrea(Long idUsuarioCrea) {
        auto.setUsuarioCrea(idUsuarioCrea);
        return this;
    }
}
