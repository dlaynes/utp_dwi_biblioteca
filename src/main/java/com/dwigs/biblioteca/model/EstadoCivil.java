package com.dwigs.biblioteca.model;

public enum EstadoCivil {
    casado,
    soltero,
    viudo,
    divorciado;

    public String toString(){
        return switch (this) {
            case casado -> "C";
            case soltero -> "S";
            case viudo -> "V";
            case divorciado -> "D";
            default -> "";
        };
    }

    public String toText(){
        return switch (this) {
            case casado -> "Casado/a";
            case soltero -> "Soltero/a";
            case viudo -> "Viudo/a";
            case divorciado -> "Divorciado/a";
            default -> "n/d";
        };
    };
}
