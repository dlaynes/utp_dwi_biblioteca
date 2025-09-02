package com.dwigs.biblioteca.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HistorialCambios {
    private long id;

    private long registro_id;

    private String tabla;

    private Usuario realizadoPor;

    // @JdbcTypeCode(SqlTypes.JSON)
    private String estadoAnterior;

    // @JdbcTypeCode(SqlTypes.JSON)
    private String estadoActual;

    private LocalDateTime fechaRegistro;

}
