package com.dwigs.biblioteca.model;


import java.time.LocalDateTime;

public enum GeneroLiterario {

    /*
    public static GeneroLiterario[] generosLiterarios = {
            new GeneroLiterario(1, LocalDateTime.now(), "Narrativo"),
            new GeneroLiterario(2, LocalDateTime.now(), "Poesía"),
            new GeneroLiterario(3, LocalDateTime.now(), "Dramático"),
            new GeneroLiterario(4, LocalDateTime.now(), "Didáctico"),
            new GeneroLiterario(5, LocalDateTime.now(), "Lírico"),
    };
    */

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
