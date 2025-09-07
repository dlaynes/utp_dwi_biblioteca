package com.dwigs.biblioteca.model;

import com.dwigs.biblioteca.model.converter.EstadoPrestamoAttributeConverter;
import com.dwigs.biblioteca.model.converter.LugarPrestamoAtributeConverter;
import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Prestamo {

    private long id;

    @Convert(converter = EstadoPrestamoAttributeConverter.class)
    private EstadoPrestamo estadoPrestamo;

    @Convert(converter = LugarPrestamoAtributeConverter.class)
    private LugarPrestamo lugarPrestamo;

    private LocalDateTime fechaRegistro;

    private InventarioLibro inventarioLibro;

    // Persona que solicita el libro
    private Usuario cliente;

    // Persona que entrega el libro
    private Usuario entregadoPor;

    // Persona que recupera el libro
    private Usuario recepcionadoPor;

    private LocalDateTime fechaReserva;

    private LocalDateTime fechaPrestamo;

    private LocalDateTime fechaEsperadaRetorno;

    private LocalDateTime fechaRetorno;

    private String observacionesEntrega;

    private String observacionesRecepcion;

    private boolean advertencia = false;

}
