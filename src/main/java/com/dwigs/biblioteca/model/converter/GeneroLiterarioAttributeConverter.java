package com.dwigs.biblioteca.model.converter;

import com.dwigs.biblioteca.model.GeneroLiterario;
import jakarta.persistence.AttributeConverter;

public class GeneroLiterarioAttributeConverter implements AttributeConverter<GeneroLiterario, String> {
    @Override
    public String convertToDatabaseColumn(GeneroLiterario genero) {
        return genero != null ? genero.toString() : null;
    }

    @Override
    public GeneroLiterario convertToEntityAttribute(String s) {
        if(s == null) return null;

        return switch(s) {
            case "N" -> GeneroLiterario.narrativo;
            case "D" -> GeneroLiterario.dramatico;
            case "I" -> GeneroLiterario.didactico;
            case "P" -> GeneroLiterario.poesia;
            case "L" -> GeneroLiterario.lirico;
            default -> null;
        };
    }

}
