package com.dwigs.biblioteca.model.converter;

import com.dwigs.biblioteca.model.TipoEvento;
import jakarta.persistence.AttributeConverter;

public class TipoEventoAttributeConverter implements AttributeConverter<TipoEvento,String> {
    @Override
    public String convertToDatabaseColumn(TipoEvento ev) {
        return ev != null ? ev.toString() : null;
    }

    @Override
    public TipoEvento convertToEntityAttribute(String s) {
        if(s == null) return null;

        return switch(s) {
            case "S" -> TipoEvento.seminario;
            case "C" -> TipoEvento.conferencia;
            case "T" -> TipoEvento.taller;
            default -> null;
        };
    }
}
