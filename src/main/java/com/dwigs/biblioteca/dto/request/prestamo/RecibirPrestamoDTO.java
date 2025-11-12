package com.dwigs.biblioteca.dto.request.prestamo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecibirPrestamoDTO {

    private String observacionesRetorno;

    private boolean advertencia;
}
