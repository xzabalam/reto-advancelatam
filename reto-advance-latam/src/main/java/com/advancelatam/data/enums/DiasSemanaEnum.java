package com.advancelatam.data.enums;

import java.util.HashMap;
import java.util.Map;

public enum DiasSemanaEnum {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO;

    private static final Map<String, DiasSemanaEnum> diaSemanaMap = new HashMap<>();

    static {
        diaSemanaMap.put("LUNES", DiasSemanaEnum.LUNES);
        diaSemanaMap.put("MARTES", DiasSemanaEnum.MARTES);
        diaSemanaMap.put("MIERCOLES", DiasSemanaEnum.MIERCOLES);
        diaSemanaMap.put("MIÉRCOLES", DiasSemanaEnum.MIERCOLES);
        diaSemanaMap.put("JUEVES", DiasSemanaEnum.JUEVES);
        diaSemanaMap.put("VIERNES", DiasSemanaEnum.VIERNES);
        diaSemanaMap.put("SABADO", DiasSemanaEnum.SABADO);
        diaSemanaMap.put("SÁBADO", DiasSemanaEnum.SABADO);
        diaSemanaMap.put("DOMINGO", DiasSemanaEnum.DOMINGO);
    }

    public static DiasSemanaEnum getDiaSemana(String dia) {
        System.out.println(dia);
        DiasSemanaEnum result = diaSemanaMap.get(dia.toUpperCase());
        if (result == null) {
            throw new IllegalArgumentException("Día de la semana no reconocido");
        }
        return result;
    }
}
