package com.dwigs.biblioteca.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActualizarCategoriaDTO {

    private String nombre;

    private String slug;

    private Long categoriaPadre_id;
}
