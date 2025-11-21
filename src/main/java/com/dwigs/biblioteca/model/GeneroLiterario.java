package com.dwigs.biblioteca.model;

public enum GeneroLiterario {

    narrativo,
    poesia,
    dramatico,
    didactico,
    lirico;

    public String toString(){
        return switch (this) {
            case narrativo -> "N";
            case poesia -> "P";
            case dramatico -> "D";
            case didactico -> "I";
            case lirico -> "L";
            default -> null;
        };
    };

    public String toText(){
        return switch (this) {
            case narrativo -> "Narrativo";
            case poesia -> "Poesía";
            case dramatico -> "Dramático";
            case didactico -> "Didáctico";
            case lirico -> "Lírico";
            default -> "n/d";
        };
    };
}
