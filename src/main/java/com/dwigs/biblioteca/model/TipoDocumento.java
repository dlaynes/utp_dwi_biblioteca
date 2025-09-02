package com.dwigs.biblioteca.model;

public enum TipoDocumento {
    dni,
    carnetExtranjeria,
    pasaporte;

    @Override
    public String toString() {
        return switch (this) {
            case dni -> "D";
            case carnetExtranjeria -> "E";
            case pasaporte -> "P";
            default -> "";
        };
    }

    public String toText(){
        return switch (this) {
            case dni -> "DNI";
            case carnetExtranjeria -> "Carnet extranjería";
            case pasaporte -> "Pasaporte";
            default -> "n/d";
        };
    };

}
