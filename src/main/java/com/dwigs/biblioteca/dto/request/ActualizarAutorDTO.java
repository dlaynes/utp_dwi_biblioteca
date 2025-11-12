package com.dwigs.biblioteca.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActualizarAutorDTO {
    String nombres;

    String apellidos;

    String nacionalidad;

    String comentarios;

}
