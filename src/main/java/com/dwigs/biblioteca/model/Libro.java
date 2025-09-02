package com.dwigs.biblioteca.model;

import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import com.dwigs.biblioteca.model.converter.GeneroLiterarioAttributeConverter;

@Getter
@Setter
@NoArgsConstructor
public class Libro implements TieneHistorial {
    private long id;

    private LocalDateTime fechaRegistro;

    private String titulo;

    private Autor autor;

    private String ibsm;

    private Editorial editorial;

    private Idioma idioma;

    private String nacionalidad;

    private int paginas;

    private LocalDateTime publicadoEn;

    @Convert(converter = GeneroLiterarioAttributeConverter.class)
    private GeneroLiterario generoLiterario;

}
