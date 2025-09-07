package com.dwigs.biblioteca.model;

import com.dwigs.biblioteca.model.converter.EstadoUsuarioAttributeConverter;
import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    private long id;

    private String email;

    private String password;

    private LocalDateTime fechaRegistro;

    private LocalDateTime ultimoLogin;

    @Convert(converter = EstadoUsuarioAttributeConverter.class)
    private EstadoUsuario estadoUsuario;

}
