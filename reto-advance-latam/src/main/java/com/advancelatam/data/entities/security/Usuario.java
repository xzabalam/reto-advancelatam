package com.advancelatam.data.entities.security;

import com.advancelatam.data.entities.core.impl.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@Getter
@Setter
public class Usuario extends AbstractEntity<Long> implements Serializable {
    private static final long serialVersionUID = -7310305185273678943L;

    @NotEmpty
    @Column(name = "username", nullable = false)
    @Size(min = 10, max = 25, message = "{entity.auditoria.username}")
    private String username;

    @NotEmpty
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private List<String> roles;

    @Override
    public String toString() {
        return "Usuario [" + (username != null ? "username=" + username + ", " : "")
                + (password != null ? "password=" + password : "") + "]";
    }
}
