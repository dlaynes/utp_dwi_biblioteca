package com.dwigs.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.dwigs.biblioteca.model.converter.GeneroAttributeConverter;
import com.dwigs.biblioteca.model.converter.TipoDocumentoAttributeConverter;
import com.dwigs.biblioteca.model.converter.EstadoCivilAttributeConverter;

@Getter
@Setter
@NoArgsConstructor
public class Perfil {
    private long id;

    private long usuario_id;

    private String nombres;

    private String apellidos;

    private String telefono;

    private String email_personal;

    private String numeroDocumento;

    @Convert(converter = TipoDocumentoAttributeConverter.class)
    private TipoDocumento tipoDocumento;

    @Convert(converter = EstadoCivilAttributeConverter.class)
    private EstadoCivil estadoCivil;

    @Convert(converter = GeneroAttributeConverter.class)
    private Genero genero;

}
