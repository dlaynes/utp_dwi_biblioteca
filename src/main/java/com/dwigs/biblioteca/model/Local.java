package com.dwigs.biblioteca.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
* Para simplificar las cosas, para este proyecto cada local tiene un solo lugar de lectura, y un solo depósito de libros
* */

@Getter
@Setter
@NoArgsConstructor
public class Local {
    private long id;

    private LocalDateTime fechaRegistro;

    private String nombre;

    private String ubicacion;

    private String email;

    private String telefono;

    private String ciudad;

    private String pais;

    private BigDecimal latitud;

    private BigDecimal longitud;

}
