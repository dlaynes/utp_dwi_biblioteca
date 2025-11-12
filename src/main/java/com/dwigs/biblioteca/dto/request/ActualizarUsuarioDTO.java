package com.dwigs.biblioteca.dto.request;

import com.dwigs.biblioteca.model.*;
import com.dwigs.biblioteca.model.converter.EstadoCivilAttributeConverter;
import com.dwigs.biblioteca.model.converter.EstadoUsuarioAttributeConverter;
import com.dwigs.biblioteca.model.converter.GeneroAttributeConverter;
import com.dwigs.biblioteca.model.converter.TipoDocumentoAttributeConverter;
import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ActualizarUsuarioDTO {
    private String nombres;

    private String apellidos;

    private String email;

    private String password;

    private String telefono;

    // Opcional
    private String emailPersonal;

    @Convert(converter = TipoDocumentoAttributeConverter.class)
    private TipoDocumento tipoDocumento;

    private String numeroDocumento;

    @Convert(converter = EstadoCivilAttributeConverter.class)
    private EstadoCivil estadoCivil;

    @Convert(converter = GeneroAttributeConverter.class)
    private Genero genero;

    @Convert(converter = EstadoUsuarioAttributeConverter.class)
    private EstadoUsuario estadoUsuario;

    private Set<Rol> roles = new HashSet<>();
}
