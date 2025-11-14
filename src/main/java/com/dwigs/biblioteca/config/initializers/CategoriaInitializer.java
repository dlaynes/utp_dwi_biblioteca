package com.dwigs.biblioteca.config.initializers;

import com.dwigs.biblioteca.model.Categoria;
import com.dwigs.biblioteca.repository.CategoriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Inicializa los datos de las categorías por defecto
 * */
@Configuration
public class CategoriaInitializer {

    public static List<Categoria> categorias = Arrays.asList(
        new Categoria("matematica", "Matemática", LocalDateTime.now(), "ico circle-burgundy", "fa-solid fa-ruler"),
        new Categoria("ciencias-medicas", "Ciencias Médicas", LocalDateTime.now(), "ico circle-green", "fa-solid fa-stethoscope"),
        new Categoria("ciencias-sociales", "Ciencias Sociales", LocalDateTime.now(), "ico circle-purple", "fa-solid fa-people-group"),
        new Categoria("historia", "Historia", LocalDateTime.now(), "ico circle-gold", "fa-solid fa-scroll"),
            new Categoria("programacion", "Programación", LocalDateTime.now(), "ico circle-teal", "fa-solid fa-java"),
        new Categoria("politica", "Política", LocalDateTime.now(), "ico circle-blue", "fa-solid fa-landmark"),
            new Categoria("humanidades", "Humanidades", LocalDateTime.now(), "ico circle-pink", "fa-solid fa-book-open")
    );

    @Bean
    public CommandLineRunner initCategorias(
            CategoriaRepository categoriaRepository
    ){
        return args -> {
            crearCategorias(categoriaRepository);
        };
    }

    private void crearCategorias(CategoriaRepository categoriaRepository){
        if(categoriaRepository.count() < 1){
            categoriaRepository.saveAll(categorias);
        }
    }

}
