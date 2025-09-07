package com.dwigs.biblioteca.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Editorial implements HasId {
    private long id;

    private LocalDateTime fechaRegistro;

    private String nombre;

    private String ciudad;

    private String pais;

}
