package com.dwigs.biblioteca.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActualizarInventarioLibroDTO {

    private Long libroId;

    private Integer disponibles;

    private Integer reservados;

    private Integer prestados;

    private Integer perdidos;



}
