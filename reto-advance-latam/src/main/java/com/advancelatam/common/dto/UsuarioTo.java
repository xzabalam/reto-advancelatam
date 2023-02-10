package com.advancelatam.common.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UsuarioTo {

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 20, message = "{entity.auditoria.username}")
    private String username;
}
