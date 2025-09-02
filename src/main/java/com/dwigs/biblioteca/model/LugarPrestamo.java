package com.dwigs.biblioteca.model;

public enum LugarPrestamo {
    salon,
    domicilio;

    public String toString(){
        return switch (this) {
            case salon -> "S";
            case domicilio -> "D";
            default -> "";
        };
    };

    public String toText(){
        return switch (this) {
            case salon -> "Salón";
            case domicilio -> "Domicilio";
            default -> "n/d";
        };
    };
}
