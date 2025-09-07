package com.dwigs.biblioteca.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class InventarioLibro {
    private long id;

    private LocalDateTime fechaRegistro;

    private Local local;

    private Libro libro;

    private int disponibles;

    private int reservados;

    private int prestados;

    private int perdidos;

}
