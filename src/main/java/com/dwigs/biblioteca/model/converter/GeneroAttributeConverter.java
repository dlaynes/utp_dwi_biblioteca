package com.dwigs.biblioteca.model.converter;

import com.dwigs.biblioteca.model.Genero;
import jakarta.persistence.AttributeConverter;

public class GeneroAttributeConverter implements AttributeConverter<Genero, String> {
    @Override
    public String convertToDatabaseColumn(Genero genero) {
        return genero != null ? genero.toString() : null;
    }

    @Override
    public Genero convertToEntityAttribute(String s) {
        if(s == null) return null;

        return switch(s) {
            case "M" -> Genero.masculino;
            case "F" -> Genero.femenino;
            case "" -> Genero.otro;
            default -> null;
        };
    }
}
