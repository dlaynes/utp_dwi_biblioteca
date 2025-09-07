package com.dwigs.biblioteca.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Idioma implements HasId {

    public static Idioma[] idiomas = {
            new Idioma(1, LocalDateTime.now(), "Español", "ES"),
            new Idioma(2, LocalDateTime.now(), "Inglés", "EN"),
            new Idioma(3, LocalDateTime.now(), "Frances", "FR"),
            new Idioma(4, LocalDateTime.now(), "Alemán", "DE"),
            new Idioma(5, LocalDateTime.now(), "Japonés", "JP"),
            new Idioma(6, LocalDateTime.now(), "Chino", "ZN"),
            new Idioma(7, LocalDateTime.now(), "Portugues", "PT"),
    };

    private long id;

    private LocalDateTime fechaRegistro;

    private String nombre;

    private String codigo;

    public Idioma(long id, LocalDateTime dateTime, String nombre, String codigo){
        this.id = id;
        this.fechaRegistro = dateTime;
        this.nombre = nombre;
        this.codigo = codigo;
    }

}
