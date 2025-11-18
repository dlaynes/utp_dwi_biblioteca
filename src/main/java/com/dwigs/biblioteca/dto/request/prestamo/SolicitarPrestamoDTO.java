package com.dwigs.biblioteca.dto.request.prestamo;

import com.dwigs.biblioteca.model.LugarPrestamo;
import com.dwigs.biblioteca.model.converter.LugarPrestamoAtributeConverter;
import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SolicitarPrestamoDTO {

    @Convert(converter = LugarPrestamoAtributeConverter.class)
    private LugarPrestamo lugarPrestamo;

    private Long libroId;

}
