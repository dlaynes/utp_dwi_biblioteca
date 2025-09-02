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
public class Prestamo implements TieneHistorial {

    private long id;

    @Convert(converter = EstadoPrestamoAttributeConverter.class)
    private EstadoPrestamo estadoPrestamo;

    @Convert(converter = LugarPrestamoAtributeConverter.class)
    private LugarPrestamo lugarPrestamo;

    private LocalDateTime fechaRegistro;

    private InventarioLibro inventarioLibro;

    // Persona que solicita el libro
    private Usuario cliente;

    private LocalDateTime fechaReserva;

    private LocalDateTime fechaPrestamo;

    private LocalDateTime fechaEsperadaRetorno;

    private LocalDateTime fechaRetorno;

    private String observacionesEntrega;

    private String observacionesRecepcion;

    private boolean advertencia = false;

}
