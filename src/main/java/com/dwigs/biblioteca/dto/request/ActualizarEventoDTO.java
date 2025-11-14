package com.dwigs.biblioteca.dto.request;

import com.dwigs.biblioteca.model.TipoEvento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ActualizarEventoDTO {
    private String titulo;

    private LocalDateTime fechaEvento;

    private TipoEvento tipoEvento;
}
