package com.dwigs.biblioteca.config.initializers;

import com.dwigs.biblioteca.model.Evento;
import com.dwigs.biblioteca.model.TipoEvento;
import com.dwigs.biblioteca.repository.EventoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class EventoInitializer {

    public static List<Evento> eventos = Arrays.asList(
            new Evento("Busqueda de literatura",
                    LocalDateTime.now(), LocalDateTime.of(2025, 9, 9, 12, 0, 0), TipoEvento.seminario),
            new Evento("Gestión de referencias",
                    LocalDateTime.now(), LocalDateTime.of(2025, 9, 22, 12, 0, 0), TipoEvento.taller),
            new Evento("Revistas científicas",
                    LocalDateTime.now(), LocalDateTime.of(2025, 9, 28, 12, 0, 0), TipoEvento.conferencia)
    );

    @Bean
    public CommandLineRunner initEventos(
            EventoRepository repository
    ){
        return args -> {
            crearEventos(repository);
        };
    }

    private void crearEventos(EventoRepository repository){
        if(repository.count() < 1){
            repository.saveAll(eventos);
        }
    }

}
