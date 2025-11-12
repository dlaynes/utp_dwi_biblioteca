package com.dwigs.biblioteca.dto.request.prestamo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AceptarPrestamoDTO {

    private LocalDateTime fechaEsperadaDeRetorno;

    private String observacionesEntrega;

}
