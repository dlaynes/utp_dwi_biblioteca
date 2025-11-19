package com.dwigs.biblioteca.dto.response;

import com.dwigs.biblioteca.model.EstadoPrestamo;
import com.dwigs.biblioteca.model.LugarPrestamo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservaResponseDTO {
    Long id;

    EstadoPrestamo estadoPrestamo;

    LugarPrestamo lugarPrestamo;

    LocalDateTime fechaReserva;

    LibroPublicoResponseDTO libro;

}
