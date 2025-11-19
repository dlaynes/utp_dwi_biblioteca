package com.dwigs.biblioteca.dto.request.prestamo;

import com.dwigs.biblioteca.model.LugarPrestamo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AceptarPrestamoDTO {

    private LocalDateTime fechaEsperadaDeRetorno;
    private LugarPrestamo lugarPrestamo;
    private String observacionesEntrega;

}
