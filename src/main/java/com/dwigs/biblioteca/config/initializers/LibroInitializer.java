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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Inicializa los datos de un local, sus libros y un inventario por defecto
 * */
@Configuration
public class LibroInitializer {

    public static List<Categoria> categorias = Arrays.asList(
            new Categoria("matematica", "Matemática", LocalDateTime.now(), "ico circle-burgundy", "fa-solid fa-ruler"),
            new Categoria("ciencias-medicas", "Ciencias Médicas", LocalDateTime.now(), "ico circle-green", "fa-solid fa-stethoscope"),
            new Categoria("ciencias-sociales", "Ciencias Sociales", LocalDateTime.now(), "ico circle-purple", "fa-solid fa-people-group"),
            new Categoria("historia", "Historia", LocalDateTime.now(), "ico circle-gold", "fa-solid fa-scroll"),
            new Categoria("programacion", "Programación", LocalDateTime.now(), "ico circle-teal", "fa-solid fa-java"),
            new Categoria("politica", "Política", LocalDateTime.now(), "ico circle-blue", "fa-solid fa-landmark"),
            new Categoria("humanidades", "Humanidades", LocalDateTime.now(), "ico circle-pink", "fa-solid fa-book-open")
    );

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
            LibroRepository libroRepository,
            CategoriaRepository categoriaRepository
    ) {
        return args -> {
            crearIdiomas(idiomaRepository);
            Editorial editorial = crearEditoriales(editorialRepository);
            List<Autor> autores = crearAutores(autorRepository);
            List<Categoria> categorias = crearCategorias(categoriaRepository);
            crearLibros(libroRepository, editorial, idiomas.get(0), autores, categorias);
        };
    }

    private List<Categoria> crearCategorias(CategoriaRepository categoriaRepository){
        if(categoriaRepository.count() < 1){
            return categoriaRepository.saveAll(categorias);
        }
        return categoriaRepository.findAll();
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

    private List<Autor> crearAutores(AutorRepository autorRepository){

        if(autorRepository.count() < 1){
            List<Autor> autores = new ArrayList<>();

            Autor autor = new Autor();
            autor.setNombres("Ricardo");
            autor.setApellidos("Palma");
            autor.setNacionalidad("Peruano");
            autor.setComentarios("Autor famoso de los siglos XIX - XX");
            autor = autorRepository.save(autor);
            autores.add(autor);

            Autor autor2 = new Autor();
            autor2.setNombres("James");
            autor2.setApellidos("Stewart");
            autor2.setNacionalidad("Canadiense");
            autor2.setComentarios("Doctor maestro en matemáticas");
            autor2 = autorRepository.save(autor2);
            autores.add(autor2);

            return autores;
        } else {
            return autorRepository.findAll();
        }
    }

    private void crearLibros(
            LibroRepository libroRepository, Editorial editorial, Idioma idioma, List<Autor> autores,
            List<Categoria> categorias
        ){

        if(libroRepository.count() < 1) {

            Libro libro = new Libro();
            libro.setTitulo("La Limeña");
            libro.setIbsm("em-1");
            libro.setNacionalidad("Peruana");
            libro.setIdioma(idioma);
            libro.setEditorial(editorial);
            libro.setAutor(autores.stream().filter(it -> it.getNombres().equals("Ricardo")).findFirst().orElseThrow());
            libro.setPaginas(500);
            libro.setDisponibles(2L);
            libro.setPerdidos(0L);
            libro.setReservados(0L);
            libro.setPerdidos(0L);
            libro.setGeneroLiterario(GeneroLiterario.narrativo);
            libro.setPublicadoEn(LocalDateTime.of(1922, 1, 1, 0, 0, 0));
            Set<Categoria> categorias1 = categorias.stream().filter(it -> it.getNombre().contains("Sociales")).collect(Collectors.toSet());
            libro.setCategorias(categorias1);
            libroRepository.save(libro);

            Libro libro2 = new Libro();
            libro2.setTitulo("Cálculo de una variable - cuarta edicióm");
            libro2.setIbsm("em-2");
            libro2.setNacionalidad("Canadiense");
            libro2.setIdioma(idioma);
            libro2.setEditorial(editorial);
            libro2.setAutor(autores.stream().filter(it -> it.getNombres().equals("James")).findFirst().orElseThrow());
            libro2.setPaginas(6420);
            libro2.setDisponibles(3L);
            libro2.setPerdidos(0L);
            libro2.setReservados(0L);
            libro2.setPerdidos(0L);
            libro2.setGeneroLiterario(GeneroLiterario.didactico);
            libro2.setPublicadoEn(LocalDateTime.of(1922, 1, 1, 0, 0, 0));
            Set<Categoria> categorias2 = categorias.stream().filter(it -> it.getNombre().contains("Mate")).collect(Collectors.toSet());
            libro2.setCategorias(categorias2);
            libroRepository.save(libro2);
        }
    }

}
