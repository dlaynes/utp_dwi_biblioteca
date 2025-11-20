package com.dwigs.biblioteca.model;

public enum EstadoPrestamo {
    reservado,
    cancelado,
    prestado,
    entregado,
    perdido;

    public String toString(){
        return switch (this) {
            case reservado -> "R";
            case cancelado -> "C";
            case prestado -> "A";
            case entregado -> "E";
            case perdido -> "P";
            default -> "";
        };
    };

    public String toText(){
        return switch (this) {
            case reservado -> "Reservado";
            case cancelado -> "Cancelado";
            case prestado -> "Prestado";
            case entregado -> "Entregado";
            case perdido -> "Perdido";
            default -> "n/d";
        };
    };

}
