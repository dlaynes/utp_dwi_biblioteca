package com.dwigs.biblioteca.model;

public enum TipoEvento {
    seminario,
    taller,
    conferencia;

    public String toString(){
        return switch (this) {
            case seminario -> "S";
            case taller -> "T";
            case conferencia -> "C";
            default -> null;
        };
    };

    public String toText(){
        return switch (this) {
            case seminario -> "Seminario";
            case taller -> "Taller";
            case conferencia -> "Conferencia";
            default -> "n/d";
        };
    };
}
