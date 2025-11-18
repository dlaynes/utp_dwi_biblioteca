package com.dwigs.biblioteca.config.initializers;

import com.dwigs.biblioteca.model.*;
import com.dwigs.biblioteca.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Inicializa los datos de un local, sus libros y un inventario por defecto
 * */
@Configuration
public class LibroInitializer {

    public static List<Idioma> idiomas = Arrays.asList(
            new Idioma(LocalDateTime.now(), "Español", "ES"),
            new Idioma(LocalDateTime.now(), "Inglés", "EN"),
            new Idioma(LocalDateTime.now(), "Frances", "FR"),
            new Idioma(LocalDateTime.now(), "Alemán", "DE"),
            new Idioma(LocalDateTime.now(), "Japonés", "JP"),
            new Idioma(LocalDateTime.now(), "Chino", "ZN"),
            new Idioma(LocalDateTime.now(), "Portugues", "PT")
    );

    // Agregar más datos de ejemplo según sea conveniente

    @Bean
    public CommandLineRunner initLocales(
            IdiomaRepository idiomaRepository,
            EditorialRepository editorialRepository,
            AutorRepository autorRepository,
            LibroRepository libroRepository
    ) {
        return args -> {
            crearIdiomas(idiomaRepository);
            Editorial editorial = crearEditoriales(editorialRepository);
            Autor autor = crearAutores(autorRepository);
            List<Libro> libros = crearLibros(libroRepository, editorial, idiomas.get(0), autor);
        };
    }

    private void crearIdiomas(IdiomaRepository idiomaRepository){
        if(idiomaRepository.count() < 1){
            idiomaRepository.saveAll(idiomas);
        }
    }

    private Editorial crearEditoriales(EditorialRepository editorialRepository){
        Editorial editorial;
        if(editorialRepository.count() < 1){
            editorial = new Editorial();
            editorial.setNombre("Desconocido/a");
            editorial.setPais("--");
            editorial.setCiudad("--");
            editorial = editorialRepository.save(editorial);

            Editorial editorial2 = new Editorial();
            editorial2.setNombre("Navarrete");
            editorial2.setPais("Perú");
            editorial2.setCiudad("Lima");
            editorialRepository.save(editorial2);
        } else {
            editorial = editorialRepository.findOneById(1L).orElseThrow();
        }
        return editorial;
    }

    private Autor crearAutores(AutorRepository autorRepository){
        Autor autor;
        if(autorRepository.count() < 1){
            autor = new Autor();
            autor.setNombres("Ricardo");
            autor.setApellidos("Palma");
            autor.setNacionalidad("Peruano");
            autor.setComentarios("Autor famoso de los siglos XIX - XX");
            autor = autorRepository.save(autor);
        } else {
            autor = autorRepository.findOneById(1L).orElseThrow();
        }
        return autor;
    }

    private List<Libro> crearLibros(LibroRepository libroRepository, Editorial editorial, Idioma idioma, Autor autor){
        List<Libro> libros = new ArrayList<>();

        if(libroRepository.count() < 1){
            Libro libro = new Libro();
            libro.setTitulo("La Limeña");
            libro.setIbsm("em-1");
            libro.setNacionalidad("Peruana");
            libro.setIdioma(idioma);
            libro.setEditorial(editorial);
            libro.setAutor(autor);
            libro.setPaginas(500);
            libro.setDisponibles(2L);
            libro.setPerdidos(0L);
            libro.setReservados(0L);
            libro.setPerdidos(0L);
            libro.setGeneroLiterario(GeneroLiterario.narrativo);
            libro.setPublicadoEn(LocalDateTime.of(1922,1,1,0,0,0));
            libros.add( libroRepository.save(libro));
        } else {
            Libro libro = libroRepository.findOneById(1L).orElseThrow();
            libros.add(libro);
        }
        return libros;
    }

}
