package com.dwigs.biblioteca.dto.request;

import com.dwigs.biblioteca.model.*;
import com.dwigs.biblioteca.model.converter.GeneroLiterarioAttributeConverter;
import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CrearLibroDTO {

    private String titulo;

    private long autorId;

    private String ibsm;

    private long editorialId;

    private long idiomaId;

    private String nacionalidad;

    private int paginas;

    private LocalDateTime publicadoEn;

    @Convert(converter = GeneroLiterarioAttributeConverter.class)
    private GeneroLiterario generoLiterario;
    
    public Libro obtenerLibro(){
        Libro libro = new Libro();
        libro.setTitulo(this.getTitulo());
        libro.setIbsm(this.getIbsm());
        libro.setNacionalidad(this.getNacionalidad());
        libro.setPublicadoEn(this.getPublicadoEn());
        libro.setPaginas(this.getPaginas());
        libro.setGeneroLiterario(this.getGeneroLiterario());
        return libro;
    }
}
