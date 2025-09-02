package com.dwigs.biblioteca.model.converter;

import com.dwigs.biblioteca.model.EstadoCivil;
import jakarta.persistence.AttributeConverter;

public class EstadoCivilAttributeConverter implements AttributeConverter<EstadoCivil,String> {
    @Override
    public String convertToDatabaseColumn(EstadoCivil estadoCivil) {
        return estadoCivil != null ? estadoCivil.toString() : null;
    }

    @Override
    public EstadoCivil convertToEntityAttribute(String s) {
        if(s == null) return null;

        return switch (s) {
            case "S" -> EstadoCivil.soltero;
            case "C" -> EstadoCivil.casado;
            case "V" -> EstadoCivil.viudo;
            case "D" -> EstadoCivil.divorciado;
            default -> null;
        };
    }
}
