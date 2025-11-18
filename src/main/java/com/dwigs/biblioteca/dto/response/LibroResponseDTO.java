package com.dwigs.biblioteca.dto.response;

import com.dwigs.biblioteca.model.*;
import com.dwigs.biblioteca.model.converter.GeneroLiterarioAttributeConverter;
import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class LibroResponseDTO {

    private Long id;

    private String titulo;

    private Autor autor;

    private String ibsm;

    private String imagen;

    private Editorial editorial;

    private Idioma idioma;

    private String nacionalidad;

    private int paginas;

    private Long disponibles;

    private Long reservados;

    private Long prestados;

    private Long perdidos;

    private LocalDateTime publicadoEn;

    @Convert(converter = GeneroLiterarioAttributeConverter.class)
    private GeneroLiterario generoLiterario;

    private Set<Long> categoriaIds = new HashSet<>();

    public static LibroResponseDTO convertirDesdeLibro(Libro libro) {
        LibroResponseDTO libroResponse = new LibroResponseDTO();

        libroResponse.setId(libro.getId());
        libroResponse.setTitulo(libro.getTitulo());
        libroResponse.setDisponibles(libro.getDisponibles());
        libroResponse.setPerdidos(libro.getPerdidos());
        libroResponse.setPrestados(libro.getPrestados());
        libroResponse.setReservados(libro.getReservados());
        libroResponse.setAutor(libro.getAutor());
        libroResponse.setGeneroLiterario(libro.getGeneroLiterario());
        libroResponse.setPublicadoEn(libro.getPublicadoEn());
        libroResponse.setEditorial(libro.getEditorial());
        libroResponse.setIbsm(libro.getIbsm());
        libroResponse.setIdioma(libro.getIdioma());
        libroResponse.setNacionalidad(libro.getNacionalidad());
        libroResponse.setPaginas(libro.getPaginas());
        libroResponse.setImagen(libro.getImagen());

        Set<Long> categoriaIds = libro.getCategorias().stream()
                .map(categoria -> categoria.getId())
                .collect(Collectors.toSet());
        libroResponse.setCategoriaIds(categoriaIds);
        return libroResponse;
    }

}
