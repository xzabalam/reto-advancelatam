package com.advancelatam.web.mapper;

import com.advancelatam.common.builder.AutoBuilder;
import com.advancelatam.data.entities.auto.Auto;
import com.advancelatam.data.entities.auto.TipoAuto;
import com.advancelatam.common.dto.AutoTo;

public final class AutoMapper {
    private AutoMapper() {
    }

    public static Auto toEntity(AutoTo autoTo, TipoAuto tipoAuto, Long idUsuarioCrea) {
        return AutoBuilder.newBuilder()
                .placa(autoTo.getPlaca().trim().toUpperCase())
                .modelo(autoTo.getModelo())
                .chasis(autoTo.getChasis())
                .color(autoTo.getColor())
                .tipo(tipoAuto)
                .usuarioCrea(idUsuarioCrea)
                .build();
    }

    public static AutoTo toTo(Auto auto) {
        return AutoTo.builder()
                .id(auto.getId())
                .placa(auto.getPlaca())
                .modelo(auto.getModelo())
                .chasis(auto.getChasis())
                .color(auto.getColor())
                .idTipoAuto(auto.getTipoAuto().getId())
                .build();
    }
}
