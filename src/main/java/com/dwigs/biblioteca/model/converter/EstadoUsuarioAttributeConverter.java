package com.dwigs.biblioteca.model.converter;


import com.dwigs.biblioteca.model.EstadoUsuario;
import jakarta.persistence.AttributeConverter;

public class EstadoUsuarioAttributeConverter implements AttributeConverter<EstadoUsuario,String> {
    @Override
    public String convertToDatabaseColumn(EstadoUsuario estadoUsuario) {
        return estadoUsuario != null ? estadoUsuario.toString() : null;
    }

    @Override
    public EstadoUsuario convertToEntityAttribute(String s) {
        if(s == null) return null;

        return switch (s) {
            case "N" -> EstadoUsuario.normal;
            case "S" -> EstadoUsuario.suspendido;
            default -> null;
        };
    }

}
