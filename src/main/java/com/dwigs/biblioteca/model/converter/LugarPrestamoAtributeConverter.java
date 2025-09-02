package com.dwigs.biblioteca.model.converter;

import com.dwigs.biblioteca.model.LugarPrestamo;
import jakarta.persistence.AttributeConverter;

public class LugarPrestamoAtributeConverter implements AttributeConverter<LugarPrestamo,String> {
    @Override
    public String convertToDatabaseColumn(LugarPrestamo lugarPrestamo) {
        return lugarPrestamo != null ? lugarPrestamo.toString() : null;
    }

    @Override
    public LugarPrestamo convertToEntityAttribute(String s) {
        if(s == null) return null;

        return switch (s) {
            case "S" -> LugarPrestamo.salon;
            case "D" -> LugarPrestamo.domicilio;
            default -> null;
        };
    }

}
