package com.dwigs.biblioteca.model;

public enum Genero {
    masculino,
    femenino,
    otro;

    public String toString(){
        return switch (this) {
            case masculino -> "M";
            case femenino -> "F";
            case otro -> "";
            default -> null;
        };
    };

    public String toText(){
        return switch (this) {
            case masculino -> "Masculino";
            case femenino -> "Femenino";
            case otro -> "Otro";
            default -> "n/d";
        };
    };
}
