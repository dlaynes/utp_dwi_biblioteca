package com.dwigs.biblioteca.model.converter;

import com.dwigs.biblioteca.model.EstadoPrestamo;
import jakarta.persistence.AttributeConverter;

public class EstadoPrestamoAttributeConverter implements AttributeConverter<EstadoPrestamo,String> {
    @Override
    public String convertToDatabaseColumn(EstadoPrestamo estadoPrestamo) {
        return estadoPrestamo != null ? estadoPrestamo.toString() : null;
    }

    @Override
    public EstadoPrestamo convertToEntityAttribute(String s) {
        if(s == null) return null;

        return switch (s) {
            case "R" -> EstadoPrestamo.reservado;
            case "C" -> EstadoPrestamo.cancelado;
            case "A" -> EstadoPrestamo.prestado;
            case "E" -> EstadoPrestamo.entregado;
            case "P" -> EstadoPrestamo.perdido;
            default -> null;
        };
    }

}
