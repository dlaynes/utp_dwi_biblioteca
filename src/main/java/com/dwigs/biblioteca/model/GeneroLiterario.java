package com.dwigs.biblioteca.model;

public enum GeneroLiterario {

    narrativo,
    poesia,
    dramatico,
    didactivo,
    lirico;

    public String toString(){
        return switch (this) {
            case narrativo -> "N";
            case poesia -> "P";
            case dramatico -> "D";
            case didactivo -> "I";
            case lirico -> "L";
            default -> null;
        };
    };

    public String toText(){
        return switch (this) {
            case narrativo -> "Narrativo";
            case poesia -> "Poesía";
            case dramatico -> "Dramático";
            case didactivo -> "Didáctico";
            case lirico -> "Lírico";
            default -> "n/d";
        };
    };
}
