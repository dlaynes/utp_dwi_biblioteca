package com.dwigs.biblioteca.dto.response;

import com.dwigs.biblioteca.model.EstadoPrestamo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservaResponseDTO {
    Long id;

    EstadoPrestamo estadoPrestamo;

    LocalDateTime fechaReserva;

    LibroPublicoResponseDTO libro;

}
