package com.dwigs.biblioteca.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Autor implements TieneHistorial {
    private long id;

    private LocalDateTime fechaRegistro;

    private String nombres;

    private String apellidos;

    private String nacionalidad;

    private String comentarios;

}
