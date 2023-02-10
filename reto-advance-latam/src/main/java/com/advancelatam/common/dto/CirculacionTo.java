package com.advancelatam.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CirculacionTo {
    private Boolean puedeCircular;
    private AutoTo autoTo;
    private List<PicoPlacaTo> configuracion;
}
