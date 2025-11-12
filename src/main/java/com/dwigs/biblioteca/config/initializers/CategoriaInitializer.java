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
        new Categoria("matematica", "Matemática", LocalDateTime.now()),
        new Categoria("ciencias-medicas", "Ciencias Médicas", LocalDateTime.now()),
        new Categoria("ciencias-sociales", "Ciencias Sociales", LocalDateTime.now()),
        new Categoria("historia", "Historia", LocalDateTime.now()),
        new Categoria("politica", "Política", LocalDateTime.now())
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
