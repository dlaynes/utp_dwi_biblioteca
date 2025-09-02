package com.dwigs.biblioteca.model;

public enum EstadoUsuario {
    normal,
    suspendido;

    public String toString(){
        return switch (this) {
            case normal -> "N";
            case suspendido -> "S";
            default -> "";
        };
    };

    public String toText(){
        return switch (this) {
            case normal -> "Normal";
            case suspendido -> "Suspendido";
            default -> "n/d";
        };
    };

}
